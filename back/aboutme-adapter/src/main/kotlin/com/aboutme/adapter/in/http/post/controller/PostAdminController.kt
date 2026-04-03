package com.aboutme.adapter.`in`.http.post.controller

import com.aboutme.adapter.common.annotation.AdminController
import com.aboutme.adapter.`in`.http.post.controller.api.PostAdminApi
import com.aboutme.adapter.`in`.http.post.req.PostCreateOrUpdateReq
import com.aboutme.app.post.port.`in`.PostUseCase
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@AdminController
class PostAdminController(
    private val postUseCase: PostUseCase,
) : PostAdminApi {
    @PostMapping("/posts")
    override fun create(
        @RequestBody @Validated req: PostCreateOrUpdateReq,
    ) {
        postUseCase.create(req.toCommand())
    }

    @GetMapping("/posts")
    override fun readAdminDetails(pageable: Pageable) = postUseCase.readAdminDetails(pageable)

    @PutMapping("/posts/{postId}")
    override fun update(
        @RequestBody @Validated req: PostCreateOrUpdateReq,
        @PathVariable postId: Long,
    ) {
        postUseCase.update(req.toCommand(), postId)
    }

    @PutMapping("/posts/{postId}/restore")
    override fun restore(
        @PathVariable postId: Long,
    ) = postUseCase.restore(postId)

    @DeleteMapping("/posts/{postId}")
    override fun softDelete(
        @PathVariable postId: Long,
    ) = postUseCase.softDelete(postId)

    @DeleteMapping("/posts/{postId}/hard")
    override fun hardDelete(
        @PathVariable postId: Long,
    ) = postUseCase.hardDelete(postId)
}
