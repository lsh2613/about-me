package com.aboutme.adapter.`in`.http.instruction.controller

import com.aboutme.adapter.`in`.http.instruction.controller.api.InstructionApi
import com.aboutme.adapter.`in`.http.instruction.req.InstructionReq
import com.aboutme.app.instruction.port.`in`.InstructionUseCase
import com.aboutme.app.instruction.service.dto.rep.InstructionDetailRep
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

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

    @GetMapping("/instructions")
    override fun readDetail(): InstructionDetailRep {
        return instructionUseCase.readDetail()
    }
}
