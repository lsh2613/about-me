package com.aboutme.adapter.`in`.http.activity.req

import com.aboutme.app.activity.service.dto.command.ActivitySyncCommand
import com.aboutme.core.activity.domain.ActivityType
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import java.time.LocalDate

@Schema(title = "활동이력 Sync 요청")
data class ActivitySyncReq(
    @field:Schema(title = "활동이력 ID", description = "활동이력 생성 시에는 null로 전달")
    val activityId: Long?,
    @field:Schema(title = "활동이력 이름")
    @field:NotEmpty(message = "활동이력 이름은 필수입니다.")
    val name: String,
    @field:Schema(title = "활동이력 유형")
    val activityType: ActivityType,
    @field:Schema(title = "활동이력 시작 날짜")
    val startDate: LocalDate,
    @field:Schema(title = "활동이력 종료 날짜", description = "활동이력이 진행 중인 경우 null로 전달")
    val endDate: LocalDate?,
    @field:Schema(title = "활동이력 설명")
    @field:Size(min = 1, max = 300, message = "활동이력 설명은 1자 이상 300자 이하로 입력해야 합니다.")
    val description: String,
    @field:Schema(title = "활동이력 순서", description = "활동이력 목록에서의 순서를 나타냅니다. 낮은 숫자가 먼저 표시됩니다.")
    @field:Min(value = 1, message = "활동이력 순서는 1 이상의 정수여야 합니다.")
    val seq: Int,
) {
    fun toCommand() =
        ActivitySyncCommand(
            activityId = activityId,
            name = name,
            activityType = activityType,
            startDate = startDate,
            endDate = endDate,
            description = description,
            seq = seq,
        )
}
