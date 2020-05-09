package com.pro.genericprocessor.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "service")
class ServiceConfig {

    lateinit var translatorTarget: String

    lateinit var kafkaOutTopic: String

    lateinit var kafkaInTopicFull: String
    lateinit var kafkaOutTopicFull: String

    var delayMillis: Long = 0


    fun getKafkaInTopicName (): String {
        return kafkaInTopicFull.split(":")[0]
    }

    fun getKafkaInTopicPartitions (): Int {
        return kafkaInTopicFull.split(":")[1].toInt()
    }

    fun getKafkaInTopicReplicationFactor (): Short {
        return kafkaInTopicFull.split(":")[2].toShort()
    }

    fun getKafkaOutTopicName (): String {
        return kafkaOutTopicFull.split(":")[0]
    }

    fun getKafkaOutTopicPartitions (): Int {
        return kafkaOutTopicFull.split(":")[1].toInt()
    }

    fun getKafkaOutTopicReplicationFactor (): Short {
        return kafkaOutTopicFull.split(":")[2].toShort()
    }

}


inline fun <reified T> T.logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}