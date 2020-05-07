package com.pro.genericprocessor.messaging

import com.pro.genericprocessor.config.ServiceConfig
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

interface MsgPublisher<K, V> : AutoCloseable {
    fun publish(msg: V): Long
}

@Service
class SpringKafkaMsgPublisher(val kafkaTemplate: KafkaTemplate<Long, String>,
                              val config: ServiceConfig) : MsgPublisher<Long, String> {

    private var counter: Long = 0

    override fun publish(msg: String): Long {
        val key = counter++
        kafkaTemplate.send(config.kafkaOutTopic, key, "$msg-")
        return key
    }

    override fun close() {
        TODO("Not yet implemented")
    }

}