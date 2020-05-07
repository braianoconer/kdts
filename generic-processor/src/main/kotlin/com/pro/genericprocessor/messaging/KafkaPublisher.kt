package com.pro.genericprocessor.messaging

import com.pro.genericprocessor.config.ServiceConfig
import com.pro.genericprocessor.config.logger
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.time.Duration
import java.time.Instant
import java.util.*
import kotlin.math.abs

interface MsgPublisher<K, V> : AutoCloseable {
    fun publish(msg: V): Long
}

@Service
class SpringKafkaMsgPublisher(private val kafkaTemplate: KafkaTemplate<Long, String>,
                              private val config: ServiceConfig) : MsgPublisher<Long, String> {


    private val log = logger()

    private val rnd = Random()
    private var counter: Long = 0

    override fun publish(msg: String): Long {

        val start = Instant.now()
        val key = counter++

        try {
            Thread.sleep(abs(rnd.nextLong()) % config.delayMillis)
            kafkaTemplate.send(config.kafkaOutTopic, key, "$msg-")
        } catch (e: InterruptedException) {
            log.error(e.message)
        } finally {
            //TODO micrometer.Timer
            log.debug("TODO micrometer {}",Duration.between(start, Instant.now()))
        }

        return key
    }

    override fun close() {
        TODO("Not yet implemented")
    }

}