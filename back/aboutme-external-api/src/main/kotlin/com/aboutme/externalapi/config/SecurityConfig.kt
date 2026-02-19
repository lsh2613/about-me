package com.aboutme.externalapi.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.security.ConditionalOnDefaultWebSecurity
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@ConditionalOnDefaultWebSecurity
@EnableWebSecurity
@EnableMethodSecurity
@Configuration
class SecurityConfig(
    private val bCryptPasswordEncoder: PasswordEncoder,
    private val basicAccessDeniedHandler: AccessDeniedHandler,
    private val basicAuthenticationEntryPoint: AuthenticationEntryPoint,
) {
    @Value("\${server.admin.username}")
    private lateinit var adminUsername: String

    @Value("\${server.admin.password}")
    private lateinit var adminPassword: String

    @Value("#{'\${server.cors.allowed-origins}'.replaceAll(' ', '').split(',')}")
    private lateinit var allowedOrigins: MutableList<String?>

    @Bean
    fun adminUserDetailsService(): UserDetailsService {
        val encodedPassword: String = bCryptPasswordEncoder.encode(adminPassword)
        val securityUser =
            User.builder()
                .username(adminUsername)
                .password(encodedPassword)
                .roles("ADMIN")
                .build()
        return InMemoryUserDetailsManager(securityUser)
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        return defaultSecurity(http)
            .authorizeHttpRequests {
                defaultAuthorizeHttpRequests(it) // todo ADMIN api 구분 정책 필요
                    .anyRequest().permitAll()
            }
            .httpBasic {}
            .build()
    }

    private fun defaultSecurity(http: HttpSecurity): HttpSecurity {
        return http.csrf { it.disable() }
            .cors { corsConfigurationSource() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .formLogin { it.disable() }
            .logout { it.disable() }
            .exceptionHandling {
                it.accessDeniedHandler(basicAccessDeniedHandler)
                it.authenticationEntryPoint(basicAuthenticationEntryPoint)
            }
    }

    private fun defaultAuthorizeHttpRequests(
        registry: AuthorizeHttpRequestsConfigurer<HttpSecurity>.AuthorizationManagerRequestMatcherRegistry,
    ): AuthorizeHttpRequestsConfigurer<HttpSecurity?>.AuthorizationManagerRequestMatcherRegistry {
        return registry.requestMatchers(HttpMethod.OPTIONS, "*").permitAll()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val corsConfiguration = CorsConfiguration()
        corsConfiguration.allowedOrigins = allowedOrigins
        corsConfiguration.allowedHeaders = mutableListOf("*")
        corsConfiguration.exposedHeaders = mutableListOf("Authorization")
        corsConfiguration.allowedMethods =
            mutableListOf(
                "GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS",
            )
        corsConfiguration.allowCredentials = true

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", corsConfiguration)
        return source
    }
}
