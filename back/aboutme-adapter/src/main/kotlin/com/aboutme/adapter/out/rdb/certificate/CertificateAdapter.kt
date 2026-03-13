package com.aboutme.adapter.out.rdb.certificate

import com.aboutme.adapter.common.annotation.Adapter
import com.aboutme.adapter.out.rdb.certificate.mapper.CertificateEntityMapper
import com.aboutme.adapter.out.rdb.certificate.repository.CertificateJpaRepository
import com.aboutme.app.certificate.port.out.CertificateCommandPort
import com.aboutme.app.certificate.port.out.CertificateQueryPort
import com.aboutme.core.certificate.domain.Certificate

@Adapter
class CertificateAdapter(
    private val certificateJpaRepository: CertificateJpaRepository,
) : CertificateCommandPort, CertificateQueryPort {
    override fun loadAll(): List<Certificate> {
        return certificateJpaRepository.findAll()
            .map { CertificateEntityMapper.Companion.toDomain(it) }
    }
}
