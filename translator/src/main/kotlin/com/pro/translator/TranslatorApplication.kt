package com.pro.translator

import com.pro.translator.config.ServiceConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(ServiceConfig::class)
class TranslatorApplication

fun main(args: Array<String>) {
	runApplication<TranslatorApplication>(*args)
}
