package com.aboutme.adapter.`in`.http.certificate.req

data class CertificateUpdateReq(
    val id: Long,
    val name: String,
    val issuer: String,
    val issuedAt: String,
    val expiredAt: String,
    val orderNum: String,
)
