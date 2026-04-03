package com.aboutme.adapter.`in`.http.post.controller.api

import com.aboutme.adapter.`in`.http.post.req.PostCreateOrUpdateReq
import com.aboutme.app.post.service.dto.rep.PostAdminDetailRep
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Tag(name = "포스팅")
interface PostAdminApi {
    @Operation(summary = "포스팅 생성")
    fun create(req: PostCreateOrUpdateReq)

    @Operation(summary = "포스팅 전체 조회")
    fun readAdminDetails(pageable: Pageable): Page<PostAdminDetailRep>

    @Operation(summary = "포스팅 수정")
    fun update(
        req: PostCreateOrUpdateReq,
        postId: Long,
    )

    @Operation(summary = "포스팅 복구")
    fun restore(postId: Long)

    @Operation(summary = "포스팅 소프트 삭제")
    fun softDelete(postId: Long)

    @Operation(summary = "포스팅 하드 삭제")
    fun hardDelete(postId: Long)
}
