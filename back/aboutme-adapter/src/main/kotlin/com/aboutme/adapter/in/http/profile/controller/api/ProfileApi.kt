package com.aboutme.adapter.`in`.http.profile.controller.api

import com.aboutme.app.profile.service.dto.rep.ProfileDetailRep
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "프로필")
interface ProfileApi {
    @Operation(summary = "프로필 상세 조회")
    fun readDetail(): ProfileDetailRep
}
