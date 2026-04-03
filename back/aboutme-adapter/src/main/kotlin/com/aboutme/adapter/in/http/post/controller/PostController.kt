package com.aboutme.adapter.`in`.http.post.controller

import com.aboutme.adapter.`in`.http.post.controller.api.PostApi
import com.aboutme.app.post.port.`in`.PostUseCase
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PostController(
    private val postUseCase: PostUseCase,
) : PostApi {
    @GetMapping("/posts")
    override fun readDetails(pageable: Pageable) = postUseCase.readDetails(pageable)
}
