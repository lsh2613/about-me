package com.aboutme.adapter.`in`.http.post.controller.api

import com.aboutme.app.post.service.dto.rep.PostDetailRep
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Tag(name = "포스팅")
interface PostApi {
    @Operation(summary = "포스팅 전체 조회")
    fun readDetails(pageable: Pageable): Page<PostDetailRep>
}
