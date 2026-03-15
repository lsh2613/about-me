package com.aboutme.app.instruction.service

import com.aboutme.app.file.service.dto.rep.FileUploadRep
import com.aboutme.app.file.service.helper.FileNamer
import com.aboutme.app.file.service.util.FileManager
import com.aboutme.app.instruction.port.`in`.InstructionUseCase
import com.aboutme.app.instruction.port.out.InstructionCommandPort
import com.aboutme.app.instruction.port.out.InstructionQueryPort
import com.aboutme.app.instruction.service.dto.command.InstructionCommand
import com.aboutme.app.instruction.service.dto.rep.InstructionDetailRep
import com.aboutme.common.extension.logger
import com.aboutme.core.file.domain.FileUploadType
import com.aboutme.core.instruction.domain.Instruction
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path

@Service
class InstructionService(
    private val instructionQueryPort: InstructionQueryPort,
    private val instructionCommandPort: InstructionCommandPort,
    private val fileNamer: FileNamer,
) : InstructionUseCase {
    private val log = logger()

    @Transactional
    override fun createOrUpdate(command: InstructionCommand) {
        instructionQueryPort.findOrNull()?.apply {
            update(command)
        } ?: save(command)
    }

    private fun Instruction.update(command: InstructionCommand) {
        update(
            name = command.name,
            emails = command.emails,
            region = command.region,
            education = command.education,
            skills = command.skills,
        )
        instructionCommandPort.update(this)
    }

    private fun save(command: InstructionCommand) {
        instructionCommandPort.save(command.toDomain())
    }

    @Transactional(readOnly = true)
    override fun readDetail(): InstructionDetailRep {
        val instruction = instructionQueryPort.findOrThrow()
        return InstructionDetailRep.from(instruction)
    }

    @Transactional
    override fun replaceProfileImage(img: MultipartFile): FileUploadRep {
        val profileType = FileUploadType.PROFILE
        require(profileType.isValidMimeType(img.contentType!!)) { "지원하는 파일 형식이 아닙니다." }

        deleteProfileImage(profileType)
        val filePath = uploadProfileImage(profileType, img)

        val uri = fileNamer.toUri(filePath)
        return FileUploadRep(uri.toString())
    }

    private fun deleteProfileImage(profileType: FileUploadType) {
        fileNamer.createUploadDirPath(profileType).also {
            log.info("Deleting existing profile image at path: $it")
            FileManager.deleteIfExists(it)
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
                instructionCommandPort.updateProfile(it.toString())
            }
        return filePath
    }

    @Transactional
    override fun deleteProfileImage() {
        val filePath = fileNamer.createUploadDirPath(FileUploadType.PROFILE)
        FileManager.deleteIfExists(filePath)

        instructionCommandPort.deleteProfile()
    }
}
