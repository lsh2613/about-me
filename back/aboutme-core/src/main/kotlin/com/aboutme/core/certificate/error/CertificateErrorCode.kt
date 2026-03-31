package com.aboutme.core.certificate.error

import com.aboutme.common.exception.payload.BaseErrorCode
import com.aboutme.common.exception.payload.ReasonCode
import com.aboutme.externalapi.exception.payload.StatusCode

enum class CertificateErrorCode(
    override val statusCode: StatusCode,
    override val reasonCode: ReasonCode,
    override val message: String,
) : BaseErrorCode {
    NOT_FOUND(StatusCode.NOT_FOUND, ReasonCode.REQUESTED_RESOURCE_NOT_FOUND, "자격증이 존재하지 않습니다."),
}
