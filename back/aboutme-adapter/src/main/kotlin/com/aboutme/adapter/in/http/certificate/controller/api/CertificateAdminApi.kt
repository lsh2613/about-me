package com.aboutme.adapter.`in`.http.certificate.controller.api

import com.aboutme.adapter.`in`.http.certificate.req.CertificateSyncReq
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "자격증")
interface CertificateAdminApi {
    @Operation(summary = "자격증 동기화")
    fun sync(reqs: List<CertificateSyncReq>)

    @Operation(summary = "자격증 전체 삭제")
    fun deleteAll()
}
