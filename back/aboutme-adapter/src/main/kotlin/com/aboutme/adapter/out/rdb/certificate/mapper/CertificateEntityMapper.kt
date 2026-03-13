package com.aboutme.adapter.out.rdb.certificate.mapper

import com.aboutme.adapter.out.rdb.certificate.entity.CertificateEntity
import com.aboutme.core.certificate.domain.Certificate

class CertificateEntityMapper {
    companion object {
        fun toDomain(entity: CertificateEntity): Certificate {
            return Certificate()
        }
    }
}
