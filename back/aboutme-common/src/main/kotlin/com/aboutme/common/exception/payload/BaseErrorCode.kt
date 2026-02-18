package com.aboutme.common.exception.payload

import com.aboutme.externalapi.exception.payload.StatusCode

/**
 * 커스텀 에러 코드를 정의하기 위한 인터페이스
 * @author LSH
 */
interface BaseErrorCode {
    val statusCode: StatusCode
    val reasonCode: ReasonCode
    val message: String

    val causedBy: CausedBy
        get() = CausedBy(statusCode, reasonCode)

    val status: Int
        get() = statusCode.code
}
