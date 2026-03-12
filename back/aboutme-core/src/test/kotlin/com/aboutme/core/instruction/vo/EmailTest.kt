package com.aboutme.core.instruction.vo

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec

class EmailTest : DescribeSpec({
    describe("Email 검증") {
        context("유효한 이메일 형식이면") {
            val validEmails =
                listOf(
                    "admin@admin.com",
                    "user123@domain.net",
                    "user_name@sub.domain.org",
                    "first-last@domain.co",
                    "first.last@domain.com",
                    "user.2024@domain.io",
                    "user@mail.server.com",
                    "longusername@verylongdomainname.com",
                    "simple@domain.xyz",
                    "a_b.c-d@sub.domain.co",
                )

            it("정상적으로 VO 객체 생성") {
                validEmails.forEach { Email(it) }
            }
        }

        context("유효하지 않은 이메일 형식이면") {
            val invalidEmails =
                listOf(
                    "",
                    " ",
                    " user123@domain.net",
                    "user123@domain.net ",
                    "plainaddress",
                    "@missingusername.com",
                    "username@com",
                    "username@.com",
                    "username@domain.",
                    "username@domain..com",
                    "user!name@domain.com",
                    "user@domain.c",
                )
            it("예외 발생") {
                invalidEmails.forEach {
                    shouldThrow<IllegalArgumentException> { Email(it) }
                }
            }
        }
    }
})
