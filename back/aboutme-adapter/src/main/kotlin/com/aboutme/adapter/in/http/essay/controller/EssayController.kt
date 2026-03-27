package com.aboutme.adapter.`in`.http.essay.controller

import com.aboutme.adapter.`in`.http.essay.controller.api.EssayApi
import com.aboutme.adapter.`in`.http.essay.req.EssaySyncReq
import com.aboutme.app.essay.port.`in`.EssayUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class EssayController(
    private val essayUseCase: EssayUseCase,
) : EssayApi {
    @PutMapping("/essays")
    override fun sync(
        @RequestBody @Validated reqs: List<EssaySyncReq>,
    ) {
        essayUseCase.sync(reqs.map { it.toCommand() })
    }

    @GetMapping("/essays")
    override fun readAll() = essayUseCase.readAll()

    @DeleteMapping("/essays")
    override fun deleteAll() = essayUseCase.deleteAll()
}
