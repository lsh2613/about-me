package com.aboutme.adapter.`in`.http.essay.controller

import com.aboutme.adapter.`in`.http.essay.controller.api.EssayApi
import com.aboutme.app.essay.port.`in`.EssayUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class EssayController(
    private val essayUseCase: EssayUseCase,
) : EssayApi {
    @GetMapping("/essays")
    override fun readAll() = essayUseCase.readAll()
}
