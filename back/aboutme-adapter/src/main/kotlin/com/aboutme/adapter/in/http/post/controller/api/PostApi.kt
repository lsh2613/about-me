package com.aboutme.adapter.`in`.http.post.controller.api

import com.aboutme.app.post.service.dto.rep.PostDetailRep
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag

@Tag(name = "포스팅")
interface PostApi {
    @Operation(summary = "게시글 전체 조회")
    fun readDetails(): List<PostDetailRep>
}
