package com.aboutme.app.instruction.service

import com.aboutme.app.common.util.InstructionMocker
import com.aboutme.app.instruction.port.out.InstructionCommandPort
import com.aboutme.app.instruction.port.out.InstructionQueryPort
import com.aboutme.app.instruction.service.dto.command.InstructionCommand
import io.kotest.core.spec.style.DescribeSpec
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class InstructionServiceTest : DescribeSpec({
    val instructionQueryPort = mockk<InstructionQueryPort>(relaxed = true)
    val instructionCommandPort = mockk<InstructionCommandPort>(relaxed = true)

    val instructionService =
        InstructionService(
            instructionQueryPort = instructionQueryPort,
            instructionCommandPort = instructionCommandPort,
        )

    describe("자기소개 생성/수정") {
        val instruction = InstructionMocker.createMock1()

        context("자기소개가 존재하지 않으면") {
            every { instructionQueryPort.findOrNull() } returns null

            with(
                InstructionCommand(
                    name = instruction.name,
                    emails = instruction.emails,
                    region = instruction.region,
                    education = instruction.education,
                    skills = instruction.skills,
                ),
            ) { instructionService.createOrUpdate(this) }

            it("생성한다") {
                verify {
                    instructionCommandPort.save(any(instruction::class))
                }
            }
        }

        context("자기소개가 존재하면") {
            every { instructionQueryPort.findOrNull() } returns instruction
            val update = InstructionMocker.createMock2()

            with(
                InstructionCommand(
                    name = update.name,
                    emails = update.emails,
                    region = update.region,
                    education = update.education,
                    skills = update.skills,
                ),
            ) { instructionService.createOrUpdate(this) }

            it("수정한다") {
                verify {
                    instructionCommandPort.update(any(instruction::class))
                }
            }
        }
    }
})
