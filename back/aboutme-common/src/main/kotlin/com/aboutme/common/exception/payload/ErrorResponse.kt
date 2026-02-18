package com.aboutme.common.exception.payload

import com.fasterxml.jackson.annotation.JsonInclude
import io.swagger.v3.oas.annotations.media.Schema

@Schema(title = "API 응답 - 실패 및 에러")
data class ErrorResponse(
    @field:Schema(title = "에러 코드", description = "정의된 에러의 4자리 정수형 문자열로 상태코드(3)+이유코드(1)로 구성됩니다.", example = "40401")
    val code: String,
    @field:Schema(
        title = "에러 이유",
        description = "에러 코드의 이유코드에 해당하며, 에러 원인의 디테일한 상태값을 제공",
        example = "REQUESTED_RESOURCE_NOT_FOUND",
    )
    val reason: String,
    @field:Schema(title = "에러 메시지", description = "에러 메시지", example = "회원을 찾을 수 없습니다.")
    val message: String,
    @field:JsonInclude(JsonInclude.Include.NON_NULL)
    val fieldErrors: Any? = null,
) {
    companion object {
        fun of(errorCode: BaseErrorCode) =
            of(
                errorCode.causedBy,
                errorCode.message,
            )

        fun of(
            causedBy: CausedBy,
            message: String,
        ) = ErrorResponse(
            causedBy.code,
            causedBy.reason,
            message,
        )

        fun of(
            causedBy: CausedBy,
            message: String,
            fieldErrors: Any,
        ) = ErrorResponse(
            causedBy.code,
            causedBy.reason,
            message,
            fieldErrors,
        )
    }
}
