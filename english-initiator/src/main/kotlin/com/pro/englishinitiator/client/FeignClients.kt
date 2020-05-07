package com.pro.englishinitiator.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam


@FeignClient(value = "spanish-processor", url = "\${service.spanish-processor-url}")
interface SpanishProcessor {

    @GetMapping(path = ["/v1.0/process"])
    fun process(@RequestParam target: String = "ES", @RequestParam word: String = "hello"): String

}


@FeignClient(value = "italian-processor", url = "\${service.italian-processor-url}")
interface ItalianProcessor {

    @GetMapping(path = ["/v1.0/process"])
    fun process(@RequestParam target: String = "IT", @RequestParam word: String = "hello"): String

}


@FeignClient(value = "german-processor", url = "\${service.german-processor-url}")
interface GermanProcessor {

    @GetMapping(path = ["/v1.0/process"])
    fun process(@RequestParam target: String = "DE", @RequestParam word: String = "hello"): String

}