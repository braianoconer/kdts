package com.pro.genericprocessor.client

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam

@FeignClient(value = "translator", url = "\${service.translator-url}")
interface Translator {

    @GetMapping(path = ["/v1.0/translate"])
    fun translate(@RequestParam target: String = "ES", @RequestParam word: String = "hello"): String

}