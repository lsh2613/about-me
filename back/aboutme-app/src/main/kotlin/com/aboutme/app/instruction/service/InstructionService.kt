package com.aboutme.app.instruction.service

import com.aboutme.app.file.service.dto.rep.FileUploadRep
import com.aboutme.app.file.service.helper.FileManager
import com.aboutme.app.file.service.helper.FileNamer
import com.aboutme.app.instruction.port.`in`.InstructionUseCase
import com.aboutme.app.instruction.port.out.InstructionCommandPort
import com.aboutme.app.instruction.port.out.InstructionQueryPort
import com.aboutme.app.instruction.service.dto.command.InstructionCommand
import com.aboutme.app.instruction.service.dto.rep.InstructionDetailRep
import com.aboutme.core.file.domain.FileUploadType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile

@Service
class InstructionService(
    private val instructionQueryPort: InstructionQueryPort,
    private val instructionCommandPort: InstructionCommandPort,
    private val fileNamer: FileNamer,
) : InstructionUseCase {
    @Transactional
    override fun createOrUpdate(command: InstructionCommand) {
        instructionQueryPort.findOrNull()?.apply {
            update(
                name = command.name,
                emails = command.emails,
                region = command.region,
                education = command.education,
                skills = command.skills,
            )
            instructionCommandPort.update(this)
        } ?: let {
            instructionCommandPort.save(command.toDomain())
        }
    }

    @Transactional(readOnly = true)
    override fun readDetail(): InstructionDetailRep {
        val instruction = instructionQueryPort.findOrThrow()
        return InstructionDetailRep.from(instruction)
    }

    @Transactional
    override fun replaceProfileImage(img: MultipartFile): FileUploadRep {
        val profileType = FileUploadType.PROFILE
        profileType.isValidMimeType(img.contentType!!)

        val filePath = fileNamer.createPath(profileType, 0L, img)
        FileManager.replaceFile(filePath, img)

        instructionCommandPort.updateProfile(filePath.toString())

        val uri = fileNamer.toUri(filePath)
        return FileUploadRep(uri.toString())
    }

    @Transactional
    override fun deleteProfileImage() {
        val filePath = fileNamer.createPath(FileUploadType.PROFILE, 0L)
        FileManager.deleteIfExists(filePath)

        instructionCommandPort.deleteProfile()
    }
}
