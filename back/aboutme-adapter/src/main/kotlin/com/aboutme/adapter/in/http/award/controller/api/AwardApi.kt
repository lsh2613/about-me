package com.aboutme.adapter.`in`.http.award.controller.api

import com.aboutme.app.award.service.dto.rep.AwardDetailRep
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "수상이력")
interface AwardApi {
    @Operation(summary = "수상이력 전체 조회")
    fun readDetails(): List<AwardDetailRep>
}
