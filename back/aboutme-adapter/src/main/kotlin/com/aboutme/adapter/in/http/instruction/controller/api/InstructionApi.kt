package com.aboutme.adapter.`in`.http.instruction.controller.api

import com.aboutme.adapter.`in`.http.instruction.req.InstructionReq
import com.aboutme.app.instruction.service.dto.rep.InstructionDetailRep
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "[자기소개]")
interface InstructionApi {
    @Operation(summary = "자기소개 생성/수정")
    fun createOrUpdate(req: InstructionReq)

    @Operation(summary = "자기소개 상세 조회")
    fun readDetail(): InstructionDetailRep
}
