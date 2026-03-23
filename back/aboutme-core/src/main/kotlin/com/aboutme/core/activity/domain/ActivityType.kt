package com.aboutme.core.activity.domain

import com.fasterxml.jackson.annotation.JsonValue

enum class ActivityType(
    val desc: String,
) {
    PART_TIME("아르바이트"),
    CAMPUS("교내활동"),
    EXTRACURRICULAR("교외활동"),
    CLUB("동아리"),
    ETC("기타"),
    ;

    @JsonValue
    override fun toString(): String {
        return name.lowercase()
    }
}
