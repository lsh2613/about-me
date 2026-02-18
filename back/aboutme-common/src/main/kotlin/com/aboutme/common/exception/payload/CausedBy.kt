package com.aboutme.common.exception.payload

import com.aboutme.externalapi.exception.payload.StatusCode

/**
 * 에러 코드를 구성하는 상세 코드
 *
 * @param statusCode [StatusCode] 상태 코드
 * @param reasonCode [ReasonCode] 이유 코드
 * @author LSH
 */
data class CausedBy(
    private val statusCode: StatusCode,
    private val reasonCode: ReasonCode,
) {
    init {
        require(isValidCodes(statusCode.code, reasonCode.code)) { "Invalid bit count" }
    }

    val status: Int
        get() = statusCode.code

    /**
     * status code, reason code를 조합하여 에러 코드를 생성한다.
     *
     * @return String : 4자리 정수로 구성된 에러 코드
     */
    val code: String
        get() = generateCode()

    /**
     * 에러가 발생한 이유를 반환한다.
     * <br></br>
     * Reason은 사전에 예외 문서에 명시한 정보를 반환한다.
     *
     * @return String : 에러가 발생한 이유
     */
    val reason: String
        get() = reasonCode.name

    private fun generateCode(): String =
        buildString {
            append(statusCode.code)
            append(reasonCode.code)
        }

    private fun isValidCodes(
        statusCode: Int,
        reasonCode: Int,
    ): Boolean = isValidDigit(statusCode, 3) && isValidDigit(reasonCode, 1)

    private fun isValidDigit(
        number: Int,
        expectedDigit: Long,
    ): Boolean = calcDigit(number) == expectedDigit

    private fun calcDigit(number: Int): Long = number.toString().length.toLong()
}
