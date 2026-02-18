package com.aboutme.externalapi.advice

import com.aboutme.common.exception.payload.CausedBy
import com.aboutme.common.exception.payload.ErrorResponse
import com.aboutme.common.exception.payload.ReasonCode
import com.aboutme.common.extension.logger
import com.aboutme.externalapi.exception.payload.StatusCode
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import org.slf4j.Logger
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingRequestCookieException
import org.springframework.web.bind.MissingRequestHeaderException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.HandlerMethodValidationException
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

@RestControllerAdvice
class ClientExceptionAdvice {
    private val log: Logger = logger()

    /**
     * API 호출 시 'Cookie' 내에 데이터 값이 유효하지 않은 경우
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestCookieException::class)
    fun handleMissingRequestCookieException(e: MissingRequestCookieException): ErrorResponse {
        log.info("handleMissingRequestCookieException : {}", e.message)
        val causedBy = CausedBy(StatusCode.BAD_REQUEST, ReasonCode.MISSING_REQUIRED_PARAMETER)
        return ErrorResponse.of(causedBy, e.message!!)
    }

    /**
     * API 호출 시 'Method' 내에 데이터 값이 유효하지 않은 경우
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpRequestMethodNotSupportedException::class)
    fun handleHttpRequestMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ErrorResponse {
        log.info("handleHttpRequestMethodNotSupportedException : {}", e.message)
        val causedBy = CausedBy(StatusCode.BAD_REQUEST, ReasonCode.INVALID_REQUEST)
        return ErrorResponse.of(causedBy, e.message!!)
    }

    /**
     * API 호출 시 'Header' 내에 데이터 값이 유효하지 않은 경우
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingRequestHeaderException::class)
    fun handleMissingRequestHeaderException(e: MissingRequestHeaderException): ErrorResponse {
        log.info("handleMissingRequestHeaderException : {}", e.message)
        val causedBy = CausedBy(StatusCode.BAD_REQUEST, ReasonCode.MISSING_REQUIRED_PARAMETER)
        return ErrorResponse.of(causedBy, e.message!!)
    }

    /**
     * API 호출 시 'Parameter' 내에 데이터 값이 존재하지 않은 경우
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingServletRequestParameterException(e: MissingServletRequestParameterException): ErrorResponse {
        log.info("handleMissingServletRequestParameterException : {}", e.message)
        val causedBy = CausedBy(StatusCode.BAD_REQUEST, ReasonCode.MISSING_REQUIRED_PARAMETER)
        return ErrorResponse.of(causedBy, e.message!!)
    }

    /**
     * Validation 어노테이션(@Valid, @Validated) 적용 시 데이터 값이 유효하지 않은 경우
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HandlerMethodValidationException::class)
    fun handleHandlerMethodValidationException(e: HandlerMethodValidationException): ErrorResponse {
        log.info("handleHandlerMethodValidationException : {}", e.message)
        val causedBy = CausedBy(StatusCode.BAD_REQUEST, ReasonCode.MISSING_REQUIRED_PARAMETER)
        return ErrorResponse.of(causedBy, e.message!!)
    }

    /**
     * API 호출 시 객체 혹은 파라미터 데이터 값이 유효하지 않은 경우
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): ErrorResponse {
        log.info("handleMethodArgumentNotValidException: {}", e.message)
        val fieldErrors: MutableMap<String, String> = HashMap()
        for (fieldError in e.bindingResult.fieldErrors) {
            fieldErrors[fieldError.field] = fieldError.defaultMessage!!
        }

        val causedBy =
            CausedBy(
                StatusCode.UNPROCESSABLE_CONTENT,
                ReasonCode.REQUIRED_PARAMETERS_MISSING_IN_REQUEST_BODY,
            )
        return ErrorResponse.of(causedBy, "입력값 검증에 실패했습니다.", fieldErrors)
    }

    /**
     * API 호출 시 객체 혹은 파라미터 데이터 값이 유효하지 않은 경우
     */
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(e: MethodArgumentTypeMismatchException): ErrorResponse {
        log.info("handleMethodArgumentTypeMismatchException: {}", e.message)
        val type = e.requiredType!!
        val fieldErrors: MutableMap<String, String> = HashMap()
        if (type.isEnum) {
            val message = "The parameter ${e.name} must have a value among : ${type.enumConstants.joinToString(", ")}"
            fieldErrors[e.name] = message
        } else {
            fieldErrors[e.name] = "The parameter ${e.name} must have a value of type ${type.simpleName}"
        }
        val causedBy =
            CausedBy(
                StatusCode.UNPROCESSABLE_CONTENT,
                ReasonCode.TYPE_MISMATCH_ERROR_IN_REQUEST_BODY,
            )
        return ErrorResponse.of(causedBy, e.message!!, fieldErrors)
    }

    /**
     * JSON 형식의 요청 데이터를 파싱하는 과정에서 발생하는 예외를 처리하는 메서드
     */
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ErrorResponse> {
        log.info("handleHttpMessageNotReadableException : {}", e.message)
        return when (val cause = e.cause) {
            is MismatchedInputException -> {
                val causedBy =
                    CausedBy(
                        StatusCode.UNPROCESSABLE_CONTENT,
                        ReasonCode.TYPE_MISMATCH_ERROR_IN_REQUEST_BODY,
                    )
                ResponseEntity.unprocessableEntity().body(
                    ErrorResponse.of(
                        causedBy,
                        "${cause.path.first().fieldName} 필드의 값이 유효하지 않습니다.",
                    ),
                )
            }

            else -> {
                val causedBy = CausedBy(StatusCode.BAD_REQUEST, ReasonCode.MALFORMED_REQUEST_BODY)
                ResponseEntity.badRequest().body(
                    ErrorResponse.of(causedBy, e.message!!),
                )
            }
        }
    }

    /**
     * IllegalArgumentException이 발생한 경우
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(java.lang.IllegalArgumentException::class)
    fun handleIllegalArgumentException(e: java.lang.IllegalArgumentException): ErrorResponse {
        log.info("handleIllegalArgumentException : {}", e.message)
        val causedBy = CausedBy(StatusCode.BAD_REQUEST, ReasonCode.INVALID_REQUEST)
        return ErrorResponse.of(causedBy, e.message!!)
    }
}
