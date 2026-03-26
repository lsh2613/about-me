package com.aboutme.core.common.vo

import com.aboutme.common.exception.GlobalException
import com.aboutme.core.common.error.CommonErrorCode
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import java.time.LocalDate

class DateRangeTest : DescribeSpec({
    describe("DateRange 객체 생성 검증") {
        context("종료일이 시작일보다 이전인 경우") {
            it("예외가 발생한다") {
                val startDate = LocalDate.of(2024, 1, 1)
                val endDate = LocalDate.of(2023, 12, 31)

                val e = shouldThrow<GlobalException> { DateRange(startDate, endDate) }
                e.errorCode shouldBe CommonErrorCode.INVALID_PERIOD
            }
        }

        context("종료일이 시작일과 같은 경우") {
            it("예외가 발생하지 않는다") {
                val date = LocalDate.of(2024, 1, 1)

                shouldNotThrowAny { DateRange(date, date) }
            }
        }

        context("종료일이 존재하지 않거나 시작일 이후인 경우") {
            it("예외가 발생하지 않는다") {
                val startDate = LocalDate.of(2024, 1, 1)
                val endDate = LocalDate.of(2024, 1, 2)

                shouldNotThrowAny { DateRange(startDate, null) }
                shouldNotThrowAny { DateRange(startDate, endDate) }
            }
        }
    }
})
