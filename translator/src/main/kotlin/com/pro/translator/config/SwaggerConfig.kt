package com.pro.translator.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import springfox.documentation.annotations.ApiIgnore
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Value("\${expanded.project.version}")
    lateinit var appVersion: String

    @Value("\${spring.application.name}")
    lateinit var appName: String

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.pro"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(ApiInfoBuilder().title(appName).version(appVersion).build())
    }
}


@ApiIgnore
@Controller
class HomeController {

    @GetMapping(path = ["/"])
    fun home(): String {
        return "redirect:/swagger-ui.html"
    }
}