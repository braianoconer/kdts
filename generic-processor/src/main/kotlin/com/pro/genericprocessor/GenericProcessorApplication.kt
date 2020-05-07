package com.pro.genericprocessor

import com.pro.genericprocessor.config.ServiceConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.kafka.annotation.EnableKafka

@EnableKafka
@EnableFeignClients
@SpringBootApplication
@EnableConfigurationProperties(ServiceConfig::class)
class GenericProcessorApplication

fun main(args: Array<String>) {
	runApplication<GenericProcessorApplication>(*args)
}
