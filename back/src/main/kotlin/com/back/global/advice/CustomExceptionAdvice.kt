package com.back.global.advice

import com.back.global.exception.GlobalException
import com.back.global.exception.payload.ErrorResponse
import com.back.global.util.logger
import org.slf4j.Logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class CustomExceptionAdvice {
    private val log: Logger = logger()

    /**
     * 비즈니스 로직 수행 중 개발자가 직접 정의한 예외를 처리하는 메서드
     */
    @ExceptionHandler(GlobalException::class)
    fun handleGlobalException(e: GlobalException): ResponseEntity<ErrorResponse> {
        val errorCode = e.errorCode
        log.info("handleGlobalException : {}", errorCode.message)
        val response = ErrorResponse.of(errorCode)
        return ResponseEntity.status(errorCode.causedBy().status).body(response)
    }
}
