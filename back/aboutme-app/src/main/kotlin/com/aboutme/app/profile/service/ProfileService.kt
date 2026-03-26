package com.aboutme.app.profile.service

import com.aboutme.app.file.service.dto.rep.FileUploadRep
import com.aboutme.app.file.service.helper.FileNamer
import com.aboutme.app.file.service.util.FileManager
import com.aboutme.app.profile.port.`in`.ProfileUseCase
import com.aboutme.app.profile.port.out.ProfileCommandPort
import com.aboutme.app.profile.port.out.ProfileQueryPort
import com.aboutme.app.profile.service.dto.command.ProfileCommand
import com.aboutme.app.profile.service.dto.rep.ProfileDetailRep
import com.aboutme.common.extension.logger
import com.aboutme.core.file.domain.FileUploadType
import com.aboutme.core.profile.domain.Profile
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path

@Service
class ProfileService(
    private val profileQueryPort: ProfileQueryPort,
    private val profileCommandPort: ProfileCommandPort,
    private val fileNamer: FileNamer,
) : ProfileUseCase {
    private val log = logger()

    @Transactional
    override fun createOrUpdate(command: ProfileCommand) {
        profileQueryPort.findOrNull()?.apply {
            update(command)
        } ?: save(command)
    }

    private fun Profile.update(command: ProfileCommand) {
        update(
            name = command.name,
            emails = command.emails,
            region = command.region,
            education = command.education,
            skills = command.skills,
        )
        profileCommandPort.update(this)
    }

    private fun save(command: ProfileCommand) {
        profileCommandPort.save(command.toDomain())
    }

    @Transactional(readOnly = true)
    override fun readDetail(): ProfileDetailRep {
        val profile = profileQueryPort.findOrThrow()
        return ProfileDetailRep.from(profile)
    }

    @Transactional
    override fun replaceProfileImage(img: MultipartFile): FileUploadRep {
        require(FileUploadType.PROFILE.isValidMimeType(img.contentType!!)) { "지원하는 파일 형식이 아닙니다." }

        val oldProfileImagePath = profileQueryPort.findOrThrow().profileImagePath
        val newProfileImagePath =
            replaceImage(
                FileUploadType.PROFILE,
                img,
                oldProfileImagePath,
            )

        val uri = fileNamer.toUri(newProfileImagePath)
        return FileUploadRep(uri.toString())
    }

    private fun replaceImage(
        profileType: FileUploadType,
        img: MultipartFile,
        oldProfileImagePath: String?,
    ): Path {
        val newProfileImagePath = uploadProfileImage(profileType, img)
        deleteProfileImage(oldProfileImagePath)
        return newProfileImagePath
    }

    private fun deleteProfileImage(oldProfileImageUrl: String?) {
        oldProfileImageUrl?.let {
            FileManager.deleteIfExists(Path.of(it))
        }
    }

    private fun uploadProfileImage(
        profileType: FileUploadType,
        img: MultipartFile,
    ): Path {
        val filePath =
            fileNamer.createUploadFilePath(type = profileType, file = img).also {
                log.info("Uploading new profile image to path: $it")
                FileManager.upload(img, it)
                profileCommandPort.updateProfile(it.toString())
            }
        return filePath
    }

    @Transactional
    override fun deleteProfileImage() {
        val filePath = fileNamer.createUploadDirPath(FileUploadType.PROFILE)
        FileManager.deleteIfExists(filePath)

        profileCommandPort.deleteProfile()
    }
}
