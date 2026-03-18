package com.aboutme.app.instruction.service

import com.aboutme.app.common.util.InstructionMocker
import com.aboutme.app.file.service.helper.FileNamer
import com.aboutme.app.file.service.util.FileManager
import com.aboutme.app.instruction.port.out.InstructionCommandPort
import com.aboutme.app.instruction.port.out.InstructionQueryPort
import com.aboutme.app.instruction.service.dto.command.InstructionCommand
import com.aboutme.app.instruction.service.dto.rep.InstructionDetailRep
import com.aboutme.common.exception.GlobalException
import com.aboutme.core.file.domain.FileUploadType
import com.aboutme.core.instruction.error.InstructionErrorCode
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.mockk.Runs
import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path

@ExtendWith(MockKExtension::class)
class InstructionServiceTest : DescribeSpec({
    val instructionQueryPort = mockk<InstructionQueryPort>(relaxed = true)
    val instructionCommandPort = mockk<InstructionCommandPort>(relaxed = true)
    val fileNamer = mockk<FileNamer>(relaxed = true)

    val instructionService =
        InstructionService(
            instructionQueryPort = instructionQueryPort,
            instructionCommandPort = instructionCommandPort,
            fileNamer = fileNamer,
        )

    val instruction = InstructionMocker.createMock1()

    describe("자기소개 생성/수정") {
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

    describe("자기소개 상세 조회") {
        context("자기소개가 존재하면") {
            every { instructionQueryPort.findOrThrow() } returns instruction
            val result = instructionService.readDetail()

            it("조회한다") {
                verify { instructionQueryPort.findOrThrow() }
                result shouldBe
                    InstructionDetailRep(
                        name = instruction.name,
                        emails = instruction.emails.map { it.value },
                        region = instruction.region,
                        education = instruction.education,
                        skills = instruction.skills,
                        profileImageUrl = instruction.profileImagePath,
                    )
            }
        }

        context("자기소개가 존재하지 않으면") {
            every { instructionQueryPort.findOrThrow() } throws GlobalException(InstructionErrorCode.NOT_FOUND)

            it("예외가 발생한다") {
                val e = shouldThrow<GlobalException> { instructionService.readDetail() }
                verify { instructionQueryPort.findOrThrow() }
                e.errorCode shouldBe InstructionErrorCode.NOT_FOUND
            }
        }
    }

    describe("자기소개 프로필 이미지 대체") {
        val img = mockk<MultipartFile>(relaxed = true)
        val filePath =
            Path.of("test-upload/profile/test-image.png").also {
                every {
                    fileNamer.createUploadFilePath(
                        type = FileUploadType.PROFILE,
                        file = img,
                    )
                } returns it
            }

        every { instructionQueryPort.findOrThrow() } returns instruction
        mockkObject(FileManager)

        context("이미지 파일인 경우") {
            every { img.contentType } returns "image/png"
            every { FileManager.upload(img, filePath) } just Runs
            val uri =
                Path.of("http://localhost:8080/test-upload/profile").toUri().also {
                    every { fileNamer.toUri(filePath) } returns it
                }

            val result = instructionService.replaceProfileImage(img)
            it("프로필 이미지 대체 로직을 호출한다") {
                verify { FileManager.deleteIfExists(Path.of(instruction.profileImagePath!!)) }
                verify { FileManager.upload(img, filePath) }
                verify { instructionCommandPort.updateProfile(filePath.toString()) }
                result.uri shouldBe uri.toString()
            }
        }

        context("업로드 실패한 경우") {
            every { img.contentType } returns "image/png"
            every { FileManager.upload(img, filePath) } throws RuntimeException("업로드 실패")
            it("예외가 발생한다") {
                val e = shouldThrow<RuntimeException> { instructionService.replaceProfileImage(img) }
                e.message shouldBe "업로드 실패"
                verify(exactly = 0) { instructionCommandPort.updateProfile(filePath.toString()) }
                verify(exactly = 0) { FileManager.deleteIfExists(Path.of(instruction.profileImagePath!!)) }
            }
        }

        context("이미지 파일이 아닌 경우") {
            every { img.contentType } returns "application/pdf"

            it("예외가 발생한다") {
                val e = shouldThrow<IllegalArgumentException> { instructionService.replaceProfileImage(img) }
                e.message shouldBe "지원하는 파일 형식이 아닙니다."
            }
        }
    }

    describe("자기소개 프로필 이미지 삭제") {
        val dirPath = Path.of("test-upload/profile")
        every { fileNamer.createUploadDirPath(FileUploadType.PROFILE) } returns dirPath
        mockkObject(FileManager)
        every { FileManager.deleteIfExists(dirPath) } just Runs
        context("프로필 이미지를 삭제하면") {
            instructionService.deleteProfileImage()
            it("프로필 이미지 경로 삭제 로직을 호출한다") {
                verify { instructionCommandPort.deleteProfile() }
                verify { FileManager.deleteIfExists(dirPath) }
            }
        }
    }
})
