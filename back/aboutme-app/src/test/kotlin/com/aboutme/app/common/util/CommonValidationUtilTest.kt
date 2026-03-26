package com.aboutme.app.common.util

import com.aboutme.common.exception.GlobalException
import com.aboutme.core.common.error.CommonErrorCode
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class CommonValidationUtilTest : DescribeSpec({
    describe("시퀀스 검증") {
        context("seq가 1부터 시작하고 누락이 없으면") {
            val seq = listOf(2, 1, 3)

            it("예외가 발생하지 않는다") {
                shouldNotThrowAny { CommonValidationUtil.validateSequence(seq) }
            }
        }

        context("seq가 1부터 시작하지 않으면") {
            val seq = listOf(2, 3, 4)

            it("예외가 발생한다") {
                val e = shouldThrow<GlobalException> { CommonValidationUtil.validateSequence(seq) }
                e.errorCode shouldBe CommonErrorCode.INVALID_SEQ
            }
        }

        context("seq 사이에 누락이 있거나 중복이면") {
            val missingSeq = listOf(1, 3)
            val duplicatedSeq = listOf(1, 2, 2)

            it("예외가 발생한다") {
                shouldThrow<GlobalException> { CommonValidationUtil.validateSequence(missingSeq) }
                shouldThrow<GlobalException> { CommonValidationUtil.validateSequence(duplicatedSeq) }
            }
        }

        context("seq가 비어 있으면") {
            it("IllegalArgumentException이 발생한다") {
                shouldThrow<IllegalArgumentException> { CommonValidationUtil.validateSequence(emptyList()) }
            }
        }
    }
})
