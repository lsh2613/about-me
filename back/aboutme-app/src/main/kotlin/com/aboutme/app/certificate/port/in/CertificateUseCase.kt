package com.aboutme.app.certificate.port.`in`

import com.aboutme.app.certificate.service.dto.command.CertificateSyncCommand
import com.aboutme.app.certificate.service.dto.rep.CertificateDetailRep

interface CertificateUseCase {
    fun sync(commands: List<CertificateSyncCommand>)

    fun deleteAll()

    fun readAll(): List<CertificateDetailRep>
}
