package com.aboutme.adapter.`in`.http.certificate.controller.api

import com.aboutme.app.certificate.service.dto.rep.CertificateDetailRep
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "자격증")
interface CertificateApi {
    @Operation(summary = "자격증 목록 조회")
    fun readAll(): List<CertificateDetailRep>
}
