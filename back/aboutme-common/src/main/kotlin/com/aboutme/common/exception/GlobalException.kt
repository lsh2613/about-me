package com.aboutme.common.exception

import com.aboutme.common.exception.payload.BaseErrorCode

class GlobalException(
    val errorCode: BaseErrorCode,
) : RuntimeException(errorCode.message)
