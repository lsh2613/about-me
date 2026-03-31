package com.aboutme.adapter.`in`.http.certificate.controller

import com.aboutme.adapter.`in`.http.certificate.controller.api.CertificateApi
import com.aboutme.app.certificate.port.`in`.CertificateUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class CertificateController(
    private val certificateUseCase: CertificateUseCase,
) : CertificateApi {
    @GetMapping("/certificates")
    override fun readAll() = certificateUseCase.readAll()
}
