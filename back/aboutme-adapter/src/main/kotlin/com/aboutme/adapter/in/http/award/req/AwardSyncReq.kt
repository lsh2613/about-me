package com.aboutme.adapter.`in`.http.award.req

import com.aboutme.app.award.service.dto.command.AwardSyncCommand
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size
import java.time.LocalDate

@Schema(title = "수상이력 Sync 요청")
data class AwardSyncReq(
    @field:Schema(title = "수상이력 이름")
    @field:Size(min = 1, max = 50, message = "수상이력 이름은 1자 이상 50자 이하로 입력해야 합니다.")
    val name: String,
    @field:Schema(title = "수여 기관")
    @field:Size(min = 1, max = 50, message = "수여 기관은 1자 이상 50자 이하로 입력해야 합니다.")
    val issuer: String,
    @field:Schema(title = "수여 날짜")
    val issueDate: LocalDate,
    @field:Schema(title = "수상이력 설명")
    @field:Size(min = 1, max = 300, message = "수상이력 설명은 1자 이상 300자 이하로 입력해야 합니다.")
    val description: String,
    @field:Schema(title = "수상이력 순서", description = "수상이력 목록에서의 순서를 나타냅니다. 낮은 숫자가 먼저 표시됩니다.")
    @field:Size(min = 1, message = "수상이력 순서는 1 이상의 정수여야 합니다.")
    val seq: Int,
) {
    fun toCommand() =
        AwardSyncCommand(
            name = name,
            issuer = issuer,
            issueDate = issueDate,
            description = description,
            seq = seq,
        )
}
