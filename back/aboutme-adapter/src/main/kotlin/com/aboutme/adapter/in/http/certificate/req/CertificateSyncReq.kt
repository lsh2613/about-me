package com.aboutme.adapter.`in`.http.certificate.req

import com.aboutme.app.certificate.service.dto.command.CertificateSyncCommand
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Size
import java.time.LocalDate

@Schema(title = "자격증 동기화 요청")
data class CertificateSyncReq(
    @field:Schema(title = "자격증 ID")
    val id: Long,
    @field:Schema(title = "자격증 이름")
    @field:Size(min = 3, max = 100)
    val name: String,
    @field:Schema(title = "발급 기관")
    @field:Size(min = 3, max = 100)
    val issuer: String,
    @field:Schema(title = "발급 날짜")
    val issueDate: LocalDate,
    @field:Schema(title = "만료 날짜")
    val expireDate: LocalDate,
    @field:Schema(title = "순서")
    val seq: Int,
) {
    fun toCommand() =
        CertificateSyncCommand(
            id = this.id,
            name = this.name,
            issuer = this.issuer,
            issueDate = this.issueDate,
            expireDate = this.expireDate,
            seq = this.seq,
        )
}
