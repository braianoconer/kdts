package com.pro.englishinitiator.controller

import com.pro.englishinitiator.config.logger
import com.pro.englishinitiator.service.PingService
import com.pro.englishinitiator.service.TaskExecutor
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(path = ["/v1.0"])
class InitiatorController(private val pingService: PingService,
                          private val taskExecutor: TaskExecutor,
                          private val reqContext: HttpServletRequest) {


    private val log = logger()


    @GetMapping(path = ["/ping"])
    @ResponseStatus(HttpStatus.OK)
    fun ping(): String {

        logIncomingRequest(null)

        taskExecutor.processSync()

        val spanishResult = pingService.pingSpanishProcessor()
        val italianResult = pingService.pingItalianProcessor()
        val germanResult = pingService.pingGermanProcessor()

        return "ping result: spanish=$spanishResult italian=$italianResult german=$germanResult"
    }

    @GetMapping(path = ["/start"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun start(@RequestParam(value = "size", defaultValue = "10") size: Int) {

        logIncomingRequest(null)
        taskExecutor.processAsync(size)
    }

    @GetMapping(path = ["/batch"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun batch(@RequestParam(value = "size", defaultValue = "100") size: Int) {

        logIncomingRequest(null)
        taskExecutor.setBatchSize(size)
    }

    @GetMapping(path = ["/stop"])
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun stop() {

        logIncomingRequest(null)
        taskExecutor.stop()
    }

    private fun logIncomingRequest(body: Any?) {
        log.info("--> ${reqContext.method} ${reqContext.requestURI}")
        if (body != null) log.info("--> with body: $body")
    }
}