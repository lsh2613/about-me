package com.aboutme.app.common.util

import com.aboutme.common.annotation.Util
import com.aboutme.core.certificate.domain.Certificate
import com.appmattus.kotlinfixture.Fixture
import java.time.LocalDate

@Util
class CertificateFixture {
    companion object {
        fun create(
            id: Long? = 1L,
            name: String = "자격증",
            issuer: String = "발급기관",
            issueDate: LocalDate = LocalDate.parse("2023-01-01"),
            expireDate: LocalDate = LocalDate.parse("2024-01-01"),
            seq: Int = 1,
        ): Certificate {
            val fixture = Fixture()
            return fixture<Certificate> {
                property(Certificate::id) { id }
                property(Certificate::name) { name }
                property(Certificate::issuer) { issuer }
                property(Certificate::issueDate) { issueDate }
                property(Certificate::expireDate) { expireDate }
                property(Certificate::seq) { seq }
            }
        }
    }
}
