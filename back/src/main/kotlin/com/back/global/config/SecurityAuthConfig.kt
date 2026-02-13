package com.back.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class SecurityAuthConfig {
    @Bean
    fun bCryptPasswordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}
