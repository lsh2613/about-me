package com.back.domain.persistence.user.error

import com.back.global.exception.payload.BaseErrorCode
import com.back.global.exception.payload.ReasonCode
import com.back.global.exception.payload.StatusCode

enum class UserErrorCode(
    override val statusCode: StatusCode,
    override val reasonCode: ReasonCode,
    override val message: String,
) : BaseErrorCode {
    FORBIDDEN(StatusCode.FORBIDDEN, ReasonCode.ACCESS_TO_THE_REQUESTED_RESOURCE_IS_FORBIDDEN, "사용자에게 권한이 없습니다."),
}
