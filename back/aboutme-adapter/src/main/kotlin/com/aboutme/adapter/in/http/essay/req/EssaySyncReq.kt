package com.aboutme.adapter.`in`.http.essay.req

import com.aboutme.app.essay.service.dto.command.EssaySyncCommand
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size

@Schema(title = "자기소개 에세이 동기화 요청")
data class EssaySyncReq(
    @field:Schema(title = "자기소개 에세이 ID", description = "신규 생성인 경우 null")
    val id: Long? = null,
    @field:Schema(title = "자기소개 에세이 제목")
    @field:Size(min = 1, max = 100, message = "자기소개 에세이 제목은 1자 이상 100자 이하로 입력해야 합니다.")
    val title: String,
    @field:Schema(title = "자기소개 에세이 내용")
    @field:Size(min = 500, max = 1500, message = "자기소개 에세이 내용은 500자 이상 1500자 이하로 입력해야 합니다.")
    val content: String,
    @field:Schema(title = "자기소개 에세이 순서", description = "자기소개 에세이 목록에서의 순서를 나타냅니다. 낮은 숫자가 먼저 표시됩니다.")
    val seq: Int,
) {
    fun toCommand() =
        EssaySyncCommand(
            id = this.id,
            title = this.title,
            content = this.content,
            seq = this.seq,
        )
}
