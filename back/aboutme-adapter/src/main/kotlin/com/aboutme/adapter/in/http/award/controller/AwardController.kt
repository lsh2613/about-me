package com.aboutme.adapter.`in`.http.award.controller

import com.aboutme.adapter.`in`.http.award.controller.api.AwardApi
import com.aboutme.app.award.port.`in`.AwardUseCase
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AwardController(
    private val awardUseCase: AwardUseCase,
) : AwardApi {
    @GetMapping("/awards")
    override fun readDetails() = awardUseCase.readDetails()
}
