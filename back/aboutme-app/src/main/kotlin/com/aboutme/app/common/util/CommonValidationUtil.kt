package com.aboutme.app.common.util

import com.aboutme.common.exception.GlobalException
import com.aboutme.core.common.error.CommonErrorCode

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
    }
}
