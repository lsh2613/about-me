package com.aboutme.core.common.domain

import java.time.LocalDateTime

abstract class SoftDeletableDomain(
    createdAt: LocalDateTime? = null,
    updatedAt: LocalDateTime? = null,
    open val deletedAt: LocalDateTime? = null,
) : Domain(createdAt, updatedAt)
