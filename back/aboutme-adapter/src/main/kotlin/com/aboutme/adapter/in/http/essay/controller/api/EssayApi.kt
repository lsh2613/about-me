package com.aboutme.adapter.`in`.http.essay.controller.api

import com.aboutme.adapter.`in`.http.essay.req.EssaySyncReq
import com.aboutme.app.essay.service.dto.rep.EssayDetailRep
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "자기소개 에세이")
interface EssayApi {
    @Operation(summary = "자기소개 에세이 동기화")
    fun sync(reqs: List<EssaySyncReq>)

    @Operation(summary = "자기소개 에세이 전체 조회")
    fun readAll(): List<EssayDetailRep>

    @Operation(summary = "자기소개 에세이 전체 삭제")
    fun deleteAll()
}
