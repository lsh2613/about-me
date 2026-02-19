package com.aboutme.domain.business.auth.error

import com.aboutme.common.exception.payload.BaseErrorCode
import com.aboutme.common.exception.payload.ReasonCode
import com.aboutme.externalapi.exception.payload.StatusCode

enum class AuthErrorCode(
    override val statusCode: StatusCode,
    override val reasonCode: ReasonCode,
    override val message: String,
) : BaseErrorCode {
    UNAUTHORIZED(StatusCode.UNAUTHORIZED, ReasonCode.MISSING_OR_INVALID_AUTHENTICATION_CREDENTIALS, "인증 정보가 유효하지 않습니다."),
    FORBIDDEN(StatusCode.FORBIDDEN, ReasonCode.ACCESS_TO_THE_REQUESTED_RESOURCE_IS_FORBIDDEN, "사용자에게 권한이 없습니다."),
}
