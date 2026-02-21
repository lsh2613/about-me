package com.aboutme.externalapi.config

import com.aboutme.externalapi.common.handler.BasicAccessDeniedHandler
import com.aboutme.externalapi.common.handler.BasicAuthenticationEntryPoint
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler

@Configuration
class SecurityAuthConfig {
    @Bean
    fun bCryptPasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun basicAccessDeniedHandler(objectMapper: ObjectMapper): AccessDeniedHandler {
        return BasicAccessDeniedHandler(objectMapper)
    }

    @Bean
    fun basicAuthenticationEntryPoint(objectMapper: ObjectMapper): AuthenticationEntryPoint {
        return BasicAuthenticationEntryPoint(objectMapper)
    }
}
