package com.pro.genericprocessor.messaging

import com.pro.genericprocessor.config.ServiceConfig
import com.pro.genericprocessor.config.logger
import io.micrometer.core.instrument.MeterRegistry
import io.micrometer.core.instrument.Timer
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.*
import javax.annotation.PostConstruct
import kotlin.math.abs


interface MsgPublisher<K, V> : AutoCloseable {
    fun publish(msg: V): Long
}


@Service
class SpringKafkaMsgPublisher(private val kafkaTemplate: KafkaTemplate<Long, String>,
                              private val registry: MeterRegistry,
                              private val config: ServiceConfig) : MsgPublisher<Long, String> {


    private val log = logger()

    private val timer: Timer = Timer.builder( "${config.getKafkaOutTopicName()}_requests_latency")
            .publishPercentiles(0.5, 0.95, 0.99, 0.999)
            .publishPercentileHistogram()
            .minimumExpectedValue(Duration.ofMillis(1))
            .maximumExpectedValue(Duration.ofMillis(config.delayMillis + 100))
            .register(registry)

    private val rnd = Random()
    private var counter: Long = 0


    @PostConstruct
    private fun init() {
        createOutTopic()
    }

    @Bean
    fun createOutTopic(): NewTopic {
        return NewTopic(config.getKafkaOutTopicName(), config.getKafkaOutTopicPartitions(), config.getKafkaOutTopicReplicationFactor())
    }

    override fun publish(msg: String): Long {

        val start = Instant.now()
        val key = counter++

        try {
            Thread.sleep(abs(rnd.nextLong()) % config.delayMillis)
            kafkaTemplate.send(config.kafkaOutTopic, key, "$msg-")
        } catch (e: InterruptedException) {
            log.error(e.message)
        } finally {
            timer.record(Duration.between(start, Instant.now()))
        }

        return key
    }

    override fun close() {
        TODO("Not yet implemented")
    }

}