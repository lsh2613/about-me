package com.back.global.exception

import com.back.global.exception.payload.BaseErrorCode

class GlobalException(
    val errorCode: BaseErrorCode,
) : RuntimeException(errorCode.message)
