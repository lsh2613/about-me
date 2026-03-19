package com.aboutme.adapter.common.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.http.CacheControl
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.time.Duration

@Configuration
class WebConfig : WebMvcConfigurer {
    @Value("\${file.upload.base-path}")
    private lateinit var uploadBasePath: String

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("$uploadBasePath/**")
            .addResourceLocations("file:$uploadBasePath")
            .setCacheControl(cacheControl())
    }

    fun cacheControl(): CacheControl {
        return CacheControl.maxAge(Duration.ofHours(1)).cachePublic()
    }
}
