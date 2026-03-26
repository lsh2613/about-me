package com.aboutme.adapter.`in`.http.profile.controller.api

import com.aboutme.adapter.`in`.http.profile.req.ProfileReq
import com.aboutme.app.file.service.dto.rep.FileUploadRep
import com.aboutme.app.profile.service.dto.rep.ProfileDetailRep
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.multipart.MultipartFile

@Tag(name = "프로필")
interface ProfileApi {
    @Operation(summary = "프로필 생성/수정")
    fun createOrUpdate(req: ProfileReq)

    @Operation(summary = "프로필 이미지 생성/수정")
    fun replaceProfileImage(img: MultipartFile): FileUploadRep

    @Operation(summary = "프로필 상세 조회")
    fun readDetail(): ProfileDetailRep

    @Operation(summary = "프로필 이미지 삭제")
    fun deleteProfileImage()
}
