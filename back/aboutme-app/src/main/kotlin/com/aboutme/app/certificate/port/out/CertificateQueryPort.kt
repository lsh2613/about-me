package com.aboutme.app.certificate.port.out

import com.aboutme.core.certificate.domain.Certificate

interface CertificateQueryPort {
    fun loadAll(): List<Certificate>
}
