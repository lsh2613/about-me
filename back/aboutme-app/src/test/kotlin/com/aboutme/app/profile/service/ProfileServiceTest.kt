package com.aboutme.app.profile.service

import com.aboutme.app.common.util.ProfileFixture
import com.aboutme.app.file.service.helper.FileNamer
import com.aboutme.app.file.service.util.FileManager
import com.aboutme.app.profile.port.out.ProfileCommandPort
import com.aboutme.app.profile.port.out.ProfileQueryPort
import com.aboutme.app.profile.service.dto.command.ProfileCommand
import com.aboutme.app.profile.service.dto.rep.ProfileDetailRep
import com.aboutme.common.exception.GlobalException
import com.aboutme.core.file.domain.FileUploadType
import com.aboutme.core.profile.error.ProfileErrorCode
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
class ProfileServiceTest : DescribeSpec({
    val profileQueryPort = mockk<ProfileQueryPort>(relaxed = true)
    val profileCommandPort = mockk<ProfileCommandPort>(relaxed = true)
    val fileNamer = mockk<FileNamer>(relaxed = true)

    val profileService =
        ProfileService(
            profileQueryPort = profileQueryPort,
            profileCommandPort = profileCommandPort,
            fileNamer = fileNamer,
        )

    val profile = ProfileFixture.createMock1()

    describe("프로필 생성/수정") {
        context("프로필이 존재하지 않으면") {
            every { profileQueryPort.findOrNull() } returns null

            with(
                ProfileCommand(
                    name = profile.name,
                    emails = profile.emails,
                    region = profile.region,
                    education = profile.education,
                    skills = profile.skills,
                ),
            ) { profileService.createOrUpdate(this) }

            it("생성한다") {
                verify {
                    profileCommandPort.save(any(profile::class))
                }
            }
        }

        context("프로필이 존재하면") {
            every { profileQueryPort.findOrNull() } returns profile
            val update = ProfileFixture.createMock2()

            with(
                ProfileCommand(
                    name = update.name,
                    emails = update.emails,
                    region = update.region,
                    education = update.education,
                    skills = update.skills,
                ),
            ) { profileService.createOrUpdate(this) }

            it("수정한다") {
                verify {
                    profileCommandPort.update(any(profile::class))
                }
            }
        }
    }

    describe("프로필 상세 조회") {
        context("프로필 존재하면") {
            every { profileQueryPort.findOrThrow() } returns profile
            val result = profileService.readDetail()

            it("조회한다") {
                verify { profileQueryPort.findOrThrow() }
                result shouldBe
                    ProfileDetailRep(
                        name = profile.name,
                        emails = profile.emails.map { it.value },
                        region = profile.region,
                        education = profile.education,
                        skills = profile.skills,
                        profileImageUrl = profile.profileImagePath,
                    )
            }
        }

        context("프로필이 존재하지 않으면") {
            every { profileQueryPort.findOrThrow() } throws GlobalException(ProfileErrorCode.NOT_FOUND)

            it("예외가 발생한다") {
                val e = shouldThrow<GlobalException> { profileService.readDetail() }
                verify { profileQueryPort.findOrThrow() }
                e.errorCode shouldBe ProfileErrorCode.NOT_FOUND
            }
        }
    }

    describe("프로필 이미지 대체") {
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

        every { profileQueryPort.findOrThrow() } returns profile
        mockkObject(FileManager)

        context("이미지 파일인 경우") {
            every { img.contentType } returns "image/png"
            every { FileManager.upload(img, filePath) } just Runs
            val uri =
                Path.of("http://localhost:8080/test-upload/profile").toUri().also {
                    every { fileNamer.toUri(filePath) } returns it
                }

            val result = profileService.replaceProfileImage(img)
            it("프로필 이미지 대체 로직을 호출한다") {
                verify { FileManager.deleteIfExists(Path.of(profile.profileImagePath!!)) }
                verify { FileManager.upload(img, filePath) }
                verify { profileCommandPort.updateProfile(filePath.toString()) }
                result.uri shouldBe uri.toString()
            }
        }

        context("업로드 실패한 경우") {
            every { img.contentType } returns "image/png"
            every { FileManager.upload(img, filePath) } throws RuntimeException("업로드 실패")
            it("예외가 발생한다") {
                val e = shouldThrow<RuntimeException> { profileService.replaceProfileImage(img) }
                e.message shouldBe "업로드 실패"
                verify(exactly = 0) { profileCommandPort.updateProfile(filePath.toString()) }
                verify(exactly = 0) { FileManager.deleteIfExists(Path.of(profile.profileImagePath!!)) }
            }
        }

        context("이미지 파일이 아닌 경우") {
            every { img.contentType } returns "application/pdf"

            it("예외가 발생한다") {
                val e = shouldThrow<IllegalArgumentException> { profileService.replaceProfileImage(img) }
                e.message shouldBe "지원하는 파일 형식이 아닙니다."
            }
        }
    }

    describe("프로필 이미지 삭제") {
        val dirPath = Path.of("test-upload/profile")
        every { fileNamer.createUploadDirPath(FileUploadType.PROFILE) } returns dirPath

        mockkObject(FileManager)
        context("프로필 이미지를 삭제하면") {
            every { FileManager.deleteIfExists(dirPath) } just Runs
            profileService.deleteProfileImage()
            it("프로필 이미지 경로 삭제 로직을 호출한다") {
                verify { profileCommandPort.deleteProfile() }
                verify { FileManager.deleteIfExists(dirPath) }
            }
        }

        context("파일 삭제에 실패하면") {
            every { FileManager.deleteIfExists(dirPath) } throws RuntimeException("삭제 실패")
            it("예외가 발생한다") {
                val e = shouldThrow<RuntimeException> { profileService.deleteProfileImage() }
                e.message shouldBe "삭제 실패"
                verify(exactly = 0) { profileCommandPort.deleteProfile() }
            }
        }
    }
})
