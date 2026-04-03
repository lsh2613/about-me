package com.aboutme.core.common.domain

import java.time.LocalDateTime

abstract class Timestampable(
    open val createdAt: LocalDateTime? = null,
    open val updatedAt: LocalDateTime? = null,
)
