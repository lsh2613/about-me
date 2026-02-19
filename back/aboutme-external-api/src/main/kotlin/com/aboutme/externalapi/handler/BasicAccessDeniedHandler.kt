package com.aboutme.externalapi.handler

import com.aboutme.common.exception.payload.ErrorResponse
import com.aboutme.common.extension.logger
import com.aboutme.domain.business.auth.error.AuthErrorCode
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler

class BasicAccessDeniedHandler(
    private val objectMapper: ObjectMapper,
) : AccessDeniedHandler {
    private val log = logger()

    override fun handle(
        request: HttpServletRequest,
        response: HttpServletResponse,
        accessDeniedException: AccessDeniedException,
    ) {
        log.info("BasicAccessDeniedHandler handle message: {}", accessDeniedException.message)

        response.contentType = "application/json;charset=UTF-8"

        with(AuthErrorCode.FORBIDDEN) {
            response.status = this.status
            objectMapper.writeValue(response.writer, ErrorResponse.of(this))
        }
    }
}
