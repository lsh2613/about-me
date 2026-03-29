package com.aboutme.adapter.`in`.http.activity.controller.api

import com.aboutme.app.activity.service.dto.rep.ActivityDetailRep
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "활동이력")
interface ActivityApi {
    @Operation(summary = "활동이력 상세 조회")
    fun readDetails(): List<ActivityDetailRep>
}
