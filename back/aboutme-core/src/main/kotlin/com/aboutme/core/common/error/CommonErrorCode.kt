package com.aboutme.core.common.error

import com.aboutme.common.exception.payload.BaseErrorCode
import com.aboutme.common.exception.payload.ReasonCode
import com.aboutme.externalapi.exception.payload.StatusCode

enum class CommonErrorCode(
    override val statusCode: StatusCode,
    override val reasonCode: ReasonCode,
    override val message: String,
) : BaseErrorCode {
    INVALID_SEQ(StatusCode.BAD_REQUEST, ReasonCode.INVALID_REQUEST, "시퀀스 순서는 1부터 시작하며 연속적이어야 합니다."),
    INVALID_PERIOD(StatusCode.BAD_REQUEST, ReasonCode.INVALID_REQUEST, "종료일은 시작일과 같거나 이후여야 합니다."),
}
