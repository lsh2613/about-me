package com.aboutme.externalapi.handler

import com.aboutme.common.exception.payload.ErrorResponse
import com.aboutme.common.extension.logger
import com.aboutme.domain.business.auth.error.AuthErrorCode
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint

class BasicAuthenticationEntryPoint(
    private val objectMapper: ObjectMapper,
) : AuthenticationEntryPoint {
    private val log = logger()

    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException,
    ) {
        log.info(
            "BasicAuthenticationEntryPoint commence message: {}, request method: {}, request url: {}",
            authException.message,
            request.method,
            request.requestURI,
        )

        response.contentType = "application/json;charset=UTF-8"

        with(AuthErrorCode.UNAUTHORIZED) {
            response.status = this.status
            objectMapper.writeValue(response.writer, ErrorResponse.of(this))
        }
    }
}
