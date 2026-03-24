package com.aboutme.adapter.`in`.http.award.controller.api

import com.aboutme.adapter.`in`.http.award.req.AwardSyncReq
import com.aboutme.app.award.service.dto.rep.AwardDetailRep
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "수상이력")
interface AwardApi {
    @Operation(
        summary = "수상이력 동기화",
        description = """
            수상이력 동기화는 생성/수정/삭제를 모두 포함합니다.
            - 생성: id가 없는 수상이력은 새로 생성됩니다.
            - 수정: id가 있는 수상이력은 기존 수상이력과 비교하여 변경된 필드만 업데이트됩니다.
            - 삭제: 기존 수상이력 중 요청에 포함되지 않은 id를 가진 수상이력은 삭제됩니다.
        """,
    )
    fun sync(reqs: List<AwardSyncReq>)

    @Operation(summary = "수상이력 전체 조회")
    fun readDetails(): List<AwardDetailRep>

    @Operation(summary = "수상이력 전체 삭제")
    fun deleteAll()
}
