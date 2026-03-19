package com.aboutme.adapter.`in`.http.instruction.controller.api

import com.aboutme.adapter.`in`.http.instruction.req.InstructionReq
import com.aboutme.app.file.service.dto.rep.FileUploadRep
import com.aboutme.app.instruction.service.dto.rep.InstructionDetailRep
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.multipart.MultipartFile

@Tag(name = "[자기소개]")
interface InstructionApi {
    @Operation(summary = "자기소개 생성/수정")
    fun createOrUpdate(req: InstructionReq)

    @Operation(summary = "자기소개 프로필 이미지 생성/수정")
    fun replaceProfileImage(img: MultipartFile): FileUploadRep

    @Operation(summary = "자기소개 상세 조회")
    fun readDetail(): InstructionDetailRep

    @Operation(summary = "자기소개 프로필 이미지 삭제")
    fun deleteProfileImage()
}
