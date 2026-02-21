package com.aboutme.externalapi.common.advice

import com.aboutme.common.exception.payload.CausedBy
import com.aboutme.common.exception.payload.ErrorResponse
import com.aboutme.common.exception.payload.ReasonCode
import com.aboutme.common.extension.logger
import com.aboutme.domain.business.auth.error.AuthErrorCode
import com.aboutme.externalapi.exception.payload.StatusCode
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotWritableException
import org.springframework.security.authorization.AuthorizationDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.servlet.NoHandlerFoundException
import org.springframework.web.servlet.resource.NoResourceFoundException

@RestControllerAdvice
class ServerExceptionAdvice {
    private val log: Logger = logger()

    /**
     * API 호출 시 인가 관련 예외를 처리하는 메서드
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(e: AccessDeniedException): ErrorResponse {
        log.info("handleAccessDeniedException : {}", e.message)
        val causedBy =
            CausedBy(
                StatusCode.FORBIDDEN,
                ReasonCode.ACCESS_TO_THE_REQUESTED_RESOURCE_IS_FORBIDDEN,
            )
        return ErrorResponse.of(causedBy, e.message!!)
    }

    /**
     * 잘못된 URL 호출 시
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException::class)
    fun handleNoHandlerFoundException(e: NoHandlerFoundException): ErrorResponse {
        log.info("handleNoHandlerFoundException : {}", e.message)
        val causedBy = CausedBy(StatusCode.NOT_FOUND, ReasonCode.INVALID_URL_OR_ENDPOINT)
        return ErrorResponse.of(causedBy, e.message!!)
    }

    /**
     * 존재하지 않는 URL 호출 시
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoResourceFoundException::class)
    fun handleNoResourceFoundException(e: NoResourceFoundException): ErrorResponse {
        log.info("handleNoResourceFoundException : {}", e.message)
        val causedBy = CausedBy(StatusCode.NOT_FOUND, ReasonCode.INVALID_URL_OR_ENDPOINT)
        return ErrorResponse.of(causedBy, e.message!!)
    }

    /**
     * API 호출 시 데이터를 반환할 수 없는 경우
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(HttpMessageNotWritableException::class)
    fun handleHttpMessageNotWritableException(e: HttpMessageNotWritableException): ErrorResponse {
        log.info("handleHttpMessageNotWritableException : {}", e.message)
        val causedBy = CausedBy(StatusCode.INTERNAL_SERVER_ERROR, ReasonCode.UNEXPECTED_ERROR)
        return ErrorResponse.of(causedBy, e.message!!)
    }

    /**
     * NullPointerException이 발생한 경우
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(NullPointerException::class)
    fun handleNullPointerException(e: NullPointerException): ErrorResponse {
        log.info("handleNullPointerException : {}", e.message)
        val causedBy = CausedBy(StatusCode.INTERNAL_SERVER_ERROR, ReasonCode.UNEXPECTED_ERROR)
        return ErrorResponse.of(causedBy, e.message!!)
    }

    /**
     * @PreAuthorize 에서 검증 실패 시 발생하는 AuthorizationDeniedException 처리
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(AuthorizationDeniedException::class)
    fun handleAuthorizationDeniedException(e: AuthorizationDeniedException): ErrorResponse {
        log.info("handleAuthorizationDeniedException : {}", e.message)
        val errorCode = AuthErrorCode.FORBIDDEN
        return ErrorResponse.of(errorCode)
    }

    /**
     * 기타 예외가 발생한 경우
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ErrorResponse {
        log.info("{} : handleException : {}", e.javaClass, e.message)

        val causedBy = CausedBy(StatusCode.INTERNAL_SERVER_ERROR, ReasonCode.UNEXPECTED_ERROR)
        return ErrorResponse.of(causedBy, e.message ?: "알 수 없는 서버 오류가 발생했습니다.")
    }
}
