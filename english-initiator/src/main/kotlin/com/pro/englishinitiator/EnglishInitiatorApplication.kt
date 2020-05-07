package com.pro.englishinitiator

import com.pro.englishinitiator.config.ServiceConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.scheduling.annotation.EnableAsync

@EnableAsync
@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(ServiceConfig::class)
class EnglishInitiatorApplication

fun main(args: Array<String>) {
	runApplication<EnglishInitiatorApplication>(*args)
}
