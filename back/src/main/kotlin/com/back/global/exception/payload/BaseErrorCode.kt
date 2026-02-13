package com.back.global.exception.payload

/**
 * 커스텀 에러 코드를 정의하기 위한 인터페이스
 * @author LSH
 */
interface BaseErrorCode {
    val statusCode: StatusCode
    val reasonCode: ReasonCode
    val message: String

    fun causedBy(): CausedBy = CausedBy(statusCode, reasonCode)
}
