package com.aboutme.core.instruction.vo

class Email(
    val value: String,
) {
    init {
        if (!value.matches(REGEX.toRegex())) {
            throw IllegalArgumentException("Invalid email format: $value")
        }
    }

    companion object {
        private const val REGEX: String = "^\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*(\\.\\w{2,})+$"
    }
}
