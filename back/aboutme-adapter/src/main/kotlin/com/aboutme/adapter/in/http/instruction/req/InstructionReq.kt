package com.aboutme.adapter.`in`.http.instruction.req

import com.aboutme.app.instruction.service.dto.command.InstructionCommand
import com.aboutme.core.instruction.vo.Email

data class InstructionReq(
    val name: String,
    val emails: List<String>,
    val region: String,
    val education: String,
    val skills: List<String>,
) {
    fun toCommand(): InstructionCommand =
        InstructionCommand(
            name = this.name,
            emails = this.emails.map { Email(it) },
            region = this.region,
            education = this.education,
            skills = this.skills,
        )
}
