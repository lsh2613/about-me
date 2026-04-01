package com.aboutme.adapter.`in`.http.post.req

import com.aboutme.app.post.service.dto.PostCreateOrUpdateCommand
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size
import java.net.URI

@Schema(title = "포스팅 생성 및 수정 요청")
data class PostCreateOrUpdateReq(
    @field:Schema(title = "제목")
    @field:Size(min = 3, max = 50)
    val title: String,
    @field:Schema(title = "본문")
    @field:Size(min = 1)
    val content: String,
    @field:Schema(title = "참고 URL 목록")
    val referenceUrls: List<String>? = null,
) {
    fun toCommand() =
        PostCreateOrUpdateCommand(
            title = title,
            content = content,
            referenceUrls = referenceUrls?.map { URI(it).toURL() },
        )
}
