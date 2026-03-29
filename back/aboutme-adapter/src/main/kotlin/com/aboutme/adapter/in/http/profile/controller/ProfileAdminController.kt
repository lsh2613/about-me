package com.aboutme.adapter.`in`.http.profile.controller

import com.aboutme.adapter.common.annotation.AdminController
import com.aboutme.adapter.`in`.http.profile.controller.api.ProfileAdminApi
import com.aboutme.adapter.`in`.http.profile.req.ProfileReq
import com.aboutme.app.file.service.dto.rep.FileUploadRep
import com.aboutme.app.profile.port.`in`.ProfileUseCase
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.multipart.MultipartFile

@AdminController
class ProfileAdminController(
    private val profileUseCase: ProfileUseCase,
) : ProfileAdminApi {
    @PostMapping("/profiles")
    override fun createOrUpdate(
        @RequestBody req: ProfileReq,
    ) {
        profileUseCase.createOrUpdate(req.toCommand())
    }

    @PostMapping(value = ["/profiles/profile-images"], consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    override fun replaceProfileImage(
        @RequestPart img: MultipartFile,
    ): FileUploadRep {
        return profileUseCase.replaceProfileImage(img)
    }

    @DeleteMapping("/profiles/profile-images")
    override fun deleteProfileImage() {
        profileUseCase.deleteProfileImage()
    }
}
