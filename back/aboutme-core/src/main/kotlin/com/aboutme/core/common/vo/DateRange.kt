package com.aboutme.core.common.vo

import com.aboutme.common.exception.GlobalException
import com.aboutme.core.common.error.CommonErrorCode
import java.time.LocalDate

data class DateRange(
    val startDate: LocalDate,
    val endDate: LocalDate?,
) {
    init {
        endDate?.let {
            if (endDate.isBefore(startDate)) {
                throw GlobalException(CommonErrorCode.INVALID_PERIOD)
            }
        }
    }
}
