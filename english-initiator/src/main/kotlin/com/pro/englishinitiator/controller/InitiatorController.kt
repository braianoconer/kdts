package com.pro.englishinitiator.controller

import com.pro.englishinitiator.config.logger
import com.pro.englishinitiator.messaging.MsgPublisher
import com.pro.englishinitiator.service.PingService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletRequest
import kotlin.math.abs

@RestController
@RequestMapping(path = ["/v1.0"])
class InitiatorController(val pingService: PingService,
                          val msgPublisher: MsgPublisher<Long, String>,
                          val reqContext: HttpServletRequest) {

    private val log = logger()

    private val rnd = Random()

    private val english = listOf("hello", "goodbye", "good morning",
            "good night", "how are you?", "what is your name?",
            "my tailor is rich", "my house is your house")


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
        msgPublisher.publish(getRandomInput() + "-")
    }

    @GetMapping(path = ["/batch"])
    fun batch(@RequestParam(value = "size", defaultValue = "100") size: Int) {

        logIncomingRequest(null)
        TODO("Not yet implemented")
    }

    @GetMapping(path = ["/stop"])
    fun stop() {

        logIncomingRequest(null)
        TODO("Not yet implemented")
    }

    private fun getRandomInput(): String {
        return english[abs(rnd.nextInt()) % (english.size - 1)]
    }

    private fun logIncomingRequest(body: Any?) {
        log.info("--> ${reqContext.method} ${reqContext.requestURI}")
        if (body != null) log.info("--> with body: $body")
    }
}