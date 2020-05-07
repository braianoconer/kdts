package com.pro.genericprocessor.controller


import com.pro.genericprocessor.client.Translator
import com.pro.genericprocessor.config.logger
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(path = ["/v1.0"])
class ProcessorController(val translator: Translator, val reqContext: HttpServletRequest) {

    private val log = logger()

    @GetMapping(path = ["/process"])
    fun process(@RequestParam(value = "target", defaultValue = "ES") target: String,
                @RequestParam(value = "word", defaultValue = "hello") word: String): String {

        log.info("--> ${reqContext.method} ${reqContext.requestURI}")

        return translator.translate(target = target, word = word)
    }

}