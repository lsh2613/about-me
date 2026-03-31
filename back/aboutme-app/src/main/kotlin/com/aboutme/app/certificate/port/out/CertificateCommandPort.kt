package com.aboutme.app.certificate.port.out

import com.aboutme.core.certificate.domain.Certificate

interface CertificateCommandPort {
    fun deleteAll()

    fun save(certificate: Certificate)

    fun update(certificate: Certificate)

    fun delete(ids: List<Long>)
}
