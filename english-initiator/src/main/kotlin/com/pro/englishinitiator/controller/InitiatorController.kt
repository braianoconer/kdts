package com.pro.englishinitiator.controller

import com.pro.englishinitiator.config.logger
import com.pro.englishinitiator.service.PingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(path = ["/v1.0"])
class InitiatorController(val pingService: PingService, val reqContext: HttpServletRequest) {

    private val log = logger()

    @GetMapping(path = ["/ping"])
    fun ping(): String {
        logIncomingRequest(null)
        val spanishResult = pingService.pingSpanishProcessor()
        val italianResult = pingService.pingItalianProcessor()
        val germanResult = pingService.pingGermanProcessor()
        return "ping result: spanish=$spanishResult italian=$italianResult german=$germanResult"
    }

    @GetMapping(path = ["/start"])
    fun start(@RequestParam(value = "size", defaultValue = "100") size: Int) {

        logIncomingRequest(null)
        TODO()
    }

    @GetMapping(path = ["/batch"])
    fun batch(@RequestParam(value = "size", defaultValue = "100") size: Int) {

        logIncomingRequest(null)
        TODO()
    }

    @GetMapping(path = ["/stop"])
    fun stop() {

        logIncomingRequest(null)
        TODO()
    }

    private fun logIncomingRequest(body: Any?) {
        log.info("--> ${reqContext.method} ${reqContext.requestURI}")
        if (body != null) log.info("--> with body: $body")
    }
}