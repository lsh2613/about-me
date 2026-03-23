package com.aboutme.core.activity.error

import com.aboutme.common.exception.payload.BaseErrorCode
import com.aboutme.common.exception.payload.ReasonCode
import com.aboutme.externalapi.exception.payload.StatusCode

enum class ActivityErrorCode(
    override val statusCode: StatusCode,
    override val reasonCode: ReasonCode,
    override val message: String,
) : BaseErrorCode {
    INVALID_SEQ(StatusCode.BAD_REQUEST, ReasonCode.INVALID_REQUEST, "활동이력의 순서는 1부터 시작하며 연속적이어야 합니다."),
    INVALID_PERIOD(StatusCode.BAD_REQUEST, ReasonCode.INVALID_REQUEST, "활동 종료일은 시작일과 같거나 이후여야 합니다."),

    NOT_FOUND(StatusCode.NOT_FOUND, ReasonCode.REQUESTED_RESOURCE_NOT_FOUND, "활동이력이 존재하지 않습니다."),
}
