package com.aboutme.core.award.error

import com.aboutme.common.exception.payload.BaseErrorCode
import com.aboutme.common.exception.payload.ReasonCode
import com.aboutme.externalapi.exception.payload.StatusCode

enum class AwardErrorCode(
    override val statusCode: StatusCode,
    override val reasonCode: ReasonCode,
    override val message: String,
) : BaseErrorCode {
    NOT_FOUND(StatusCode.NOT_FOUND, ReasonCode.REQUESTED_RESOURCE_NOT_FOUND, "수상이력이 존재하지 않습니다."),
}
