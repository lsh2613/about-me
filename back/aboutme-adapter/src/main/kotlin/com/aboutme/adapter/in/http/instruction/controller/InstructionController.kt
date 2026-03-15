package com.aboutme.adapter.`in`.http.instruction.controller

import com.aboutme.adapter.`in`.http.instruction.controller.api.InstructionApi
import com.aboutme.adapter.`in`.http.instruction.req.InstructionReq
import com.aboutme.app.file.service.dto.rep.FileUploadRep
import com.aboutme.app.instruction.port.`in`.InstructionUseCase
import com.aboutme.app.instruction.service.dto.rep.InstructionDetailRep
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
class InstructionController(
    private val instructionUseCase: InstructionUseCase,
) : InstructionApi {
    @PostMapping("/instructions")
    override fun createOrUpdate(
        @RequestBody req: InstructionReq,
    ) {
        instructionUseCase.createOrUpdate(req.toCommand())
    }

    @PostMapping("/instructions/profile-images")
    override fun replaceProfileImage(img: MultipartFile): FileUploadRep {
        return instructionUseCase.replaceProfileImage(img)
    }

    @GetMapping("/instructions")
    override fun readDetail(): InstructionDetailRep {
        return instructionUseCase.readDetail()
    }

    @DeleteMapping("/instructions/profile-images")
    override fun deleteProfileImage() {
        instructionUseCase.deleteProfileImage()
    }
}
