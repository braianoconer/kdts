package com.pro.englishinitiator.messaging

import com.pro.englishinitiator.config.ServiceConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct


interface MsgPublisher<K, V> : AutoCloseable {
    fun publish(msg: V): Long
}


@Service
class SpringKafkaMsgPublisher(private val kafkaTemplate: KafkaTemplate<Long, String>,
                              private val config: ServiceConfig) : MsgPublisher<Long, String> {

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
        val key = counter++
        kafkaTemplate.send(config.kafkaOutTopic, key, "$key:$msg")
        return key
    }

    override fun close() {
        TODO("Not yet implemented")
    }

}