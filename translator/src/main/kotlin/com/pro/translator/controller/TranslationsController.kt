package com.pro.translator.controller

import com.pro.translator.config.ServiceConfig
import com.pro.translator.config.logger
import com.pro.translator.controller.service.Dictionary
import com.pro.translator.controller.service.TargetLanguage
import io.micrometer.core.annotation.Timed
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*
import javax.servlet.http.HttpServletRequest
import kotlin.math.abs

@RestController
@RequestMapping(path = ["/v1.0"])
class TranslationsController(private val serviceConfig: ServiceConfig,
                             private val dictionary: Dictionary,
                             private val reqContext: HttpServletRequest) {

    private val log = logger()
    private val rnd = Random()

    @Timed(
            value = "com.pro.translator.controller.translate",
            histogram = true,
            percentiles = [0.5,0.95,0.99]
    )
    @GetMapping(path = ["/translate"])
    fun translate(@RequestParam(value = "target", defaultValue = "ES") target: String,
                  @RequestParam(value = "word", defaultValue = "hello") word: String): ResponseEntity<String> {

        log.info("--> ${reqContext.method} ${reqContext.requestURI}")

        return if (serviceConfig.apiSuccessRate > 1 &&
                    (serviceConfig.apiSuccessRate >= 100 ||
                            (abs(rnd.nextInt()) % serviceConfig.apiSuccessRate != 0))) {

            try {
                val targetEnum = TargetLanguage.valueOf(target.toUpperCase())
                ResponseEntity.ok(dictionary.translateTo(targetEnum, word))
            } catch (e: IllegalArgumentException) {
                ResponseEntity.badRequest().body("target [$target] should be one of [ES, IT, DE]")
            }
        } else {
            ResponseEntity.badRequest().body("Error in translation request for $word")
        }
    }

}