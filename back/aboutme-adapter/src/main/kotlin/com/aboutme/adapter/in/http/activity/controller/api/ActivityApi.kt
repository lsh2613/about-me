package com.aboutme.adapter.`in`.http.activity.controller.api

import com.aboutme.adapter.`in`.http.activity.req.ActivitySyncReq
import com.aboutme.app.activity.service.dto.rep.ActivityDetailRep
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "활동이력")
interface ActivityApi {
    @Operation(
        summary = "활동이력 동기화",
        description = """
            활동이력 동기화는 생성/수정/삭제를 모두 포함합니다.
            - 생성: id가 없는 활동이력은 새로 생성됩니다.
            - 수정: id가 있는 활동이력은 기존 활동이력과 비교하여 변경된 필드만 업데이트됩니다.
            - 삭제: 기존 활동이력 중 요청에 포함되지 않은 id를 가진 활동이력은 삭제됩니다.
        """,
    )
    fun sync(reqs: List<ActivitySyncReq>)

    @Operation(summary = "활동이력 상세 조회")
    fun readDetails(): List<ActivityDetailRep>

    @Operation(summary = "활동이력 전체 삭제")
    fun deleteAll()
}
