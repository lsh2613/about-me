package com.aboutme.app.certificate.service

import com.aboutme.app.certificate.port.`in`.CertificateUseCase
import com.aboutme.app.certificate.port.out.CertificateCommandPort
import com.aboutme.app.certificate.port.out.CertificateQueryPort
import com.aboutme.app.certificate.service.dto.command.CertificateSyncCommand
import com.aboutme.app.certificate.service.dto.rep.CertificateDetailRep
import com.aboutme.app.common.util.CommonValidationUtil
import com.aboutme.core.certificate.domain.Certificate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CertificateService(
    private val certificateQueryPort: CertificateQueryPort,
    private val certificateCommandPort: CertificateCommandPort,
) : CertificateUseCase {
    @Transactional
    override fun sync(commands: List<CertificateSyncCommand>) {
        CommonValidationUtil.validateSequence(commands.map { it.seq })

        deleteNotInCommands(commands)
        saveOrUpdate(commands)
    }

    private fun saveOrUpdate(commands: List<CertificateSyncCommand>) {
        commands.forEach { command ->
            command.id?.let {
                update(it, command)
            } ?: save(command)
        }
    }

    private fun save(command: CertificateSyncCommand) {
        Certificate(
            name = command.name,
            issuer = command.issuer,
            issueDate = command.issueDate,
            expireDate = command.expireDate,
            seq = command.seq,
        ).also { certificateCommandPort.save(it) }
    }

    @Transactional(readOnly = true)
    override fun readAll(): List<CertificateDetailRep> {
        return certificateQueryPort.findAll().map(CertificateDetailRep::of)
    }

    private fun update(
        id: Long,
        command: CertificateSyncCommand,
    ) {
        certificateQueryPort.findOrThrow(id).apply {
            update(
                name = command.name,
                issuer = command.issuer,
                issueDate = command.issueDate,
                expireDate = command.expireDate,
                seq = command.seq,
            )
        }.also { certificateCommandPort.update(it) }
    }

    private fun deleteNotInCommands(commands: List<CertificateSyncCommand>) {
        val certificates = certificateQueryPort.findAll()
        val deleteIds = certificates.map { it.id!! } - commands.mapNotNull { it.id }.toSet()
        certificateCommandPort.delete(deleteIds)
    }

    @Transactional
    override fun deleteAll() {
        certificateCommandPort.deleteAll()
    }
}
