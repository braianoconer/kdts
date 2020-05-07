package com.pro.englishinitiator.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "service")
class ServiceConfig {

}

inline fun <reified T> T.logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}