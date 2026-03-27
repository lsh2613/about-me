package com.aboutme.app.profile.port.`in`

import com.aboutme.app.common.annotation.UseCase
import com.aboutme.app.file.service.dto.rep.FileUploadRep
import com.aboutme.app.profile.service.dto.command.ProfileCommand
import com.aboutme.app.profile.service.dto.rep.ProfileDetailRep
import org.springframework.web.multipart.MultipartFile

@UseCase
interface ProfileUseCase {
    fun createOrUpdate(command: ProfileCommand)

    fun readDetail(): ProfileDetailRep

    fun replaceProfileImage(img: MultipartFile): FileUploadRep

    fun deleteProfileImage()
}
