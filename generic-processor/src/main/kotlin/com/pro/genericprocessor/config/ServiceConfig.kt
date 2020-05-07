package com.pro.genericprocessor.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "service")
class ServiceConfig {

    lateinit var translatorTarget: String

    lateinit var kafkaInTopic: String
    lateinit var kafkaOutTopic: String
}

inline fun <reified T> T.logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}