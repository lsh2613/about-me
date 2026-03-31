package com.aboutme.adapter.`in`.http.certificate.controller

import com.aboutme.adapter.common.annotation.AdminController
import com.aboutme.adapter.`in`.http.certificate.controller.api.CertificateAdminApi
import com.aboutme.adapter.`in`.http.certificate.req.CertificateSyncReq
import com.aboutme.app.certificate.port.`in`.CertificateUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@AdminController
class CertificateAdminController(
    private val certificateUseCase: CertificateUseCase,
) : CertificateAdminApi {
    @PutMapping("/certificates")
    override fun sync(
        @RequestBody @Validated reqs: List<CertificateSyncReq>,
    ) {
        certificateUseCase.sync(reqs.map { it.toCommand() })
    }

    @DeleteMapping("/certificates")
    override fun deleteAll() = certificateUseCase.deleteAll()
}
