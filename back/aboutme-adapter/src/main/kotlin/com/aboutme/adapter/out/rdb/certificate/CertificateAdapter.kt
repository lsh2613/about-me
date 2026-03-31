package com.aboutme.adapter.out.rdb.certificate

import com.aboutme.adapter.common.annotation.Adapter
import com.aboutme.adapter.out.rdb.certificate.entity.CertificateEntity
import com.aboutme.adapter.out.rdb.certificate.mapper.CertificateMapper
import com.aboutme.adapter.out.rdb.certificate.repository.CertificateJpaRepository
import com.aboutme.app.certificate.port.out.CertificateCommandPort
import com.aboutme.app.certificate.port.out.CertificateQueryPort
import com.aboutme.common.exception.GlobalException
import com.aboutme.core.certificate.domain.Certificate
import com.aboutme.core.certificate.error.CertificateErrorCode

@Adapter
class CertificateAdapter(
    private val certificateJpaRepository: CertificateJpaRepository,
) : CertificateCommandPort, CertificateQueryPort {
    override fun save(certificate: Certificate) {
        certificateJpaRepository.save(CertificateMapper.toEntity(certificate))
    }

    override fun findAll(): List<Certificate> {
        return certificateJpaRepository.findAll()
            .map { CertificateMapper.toDomain(it) }
    }

    override fun findOrThrow(id: Long): Certificate {
        return findEntityByIdOrThrow(id).let(CertificateMapper::toDomain)
    }

    private fun findEntityByIdOrThrow(id: Long): CertificateEntity {
        return certificateJpaRepository.findById(id)
            .orElseThrow { GlobalException(CertificateErrorCode.NOT_FOUND) }
    }

    override fun update(certificate: Certificate) {
        findEntityByIdOrThrow(certificate.id!!).apply {
            update(
                name = certificate.name,
                issuer = certificate.issuer,
                issueDate = certificate.issueDate,
                expireDate = certificate.expireDate,
                seq = certificate.seq,
            )
        }
    }

    override fun delete(ids: List<Long>) = certificateJpaRepository.deleteAllById(ids)

    override fun deleteAll() = certificateJpaRepository.deleteAll()
}
