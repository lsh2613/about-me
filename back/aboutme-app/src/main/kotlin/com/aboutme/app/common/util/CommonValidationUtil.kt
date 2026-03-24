package com.aboutme.app.common.util

import com.aboutme.app.common.error.CommonErrorCode
import com.aboutme.common.exception.GlobalException
import java.time.LocalDate

class CommonValidationUtil {
    companion object {
        fun validateSequence(seq: List<Int>) {
            require(seq.isNotEmpty()) { "시퀀스 순서는 하나 이상 존재해야 합니다." }
            val sortedSeq = seq.sorted()
            val sequentialSeq = (1..seq.size).toList()
            if (sortedSeq != sequentialSeq) {
                throw GlobalException(CommonErrorCode.INVALID_SEQ)
            }
        }

        fun validateChronologicalDate(
            startDate: LocalDate,
            endDate: LocalDate?,
        ) {
            endDate?.let {
                if (it.isBefore(startDate)) {
                    throw GlobalException(CommonErrorCode.INVALID_PERIOD)
                }
            }
        }
    }
}
