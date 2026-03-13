package com.aboutme.adapter.`in`.http.award.req

data class AwardCreateOrUpdateReq(
    val name: String,
    val issuer: String,
    val issuedAt: String,
    val description: String,
)
