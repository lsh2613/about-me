package com.aboutme.externalapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.aboutme"])
class AboutMeExternalApiApplication

fun main(args: Array<String>) {
    System.setProperty(
        "spring.config.name",
        "application-ai,application-domain,application-external-api",
    )
    runApplication<AboutMeExternalApiApplication>(*args)
}
