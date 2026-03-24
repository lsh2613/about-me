package com.aboutme.adapter.`in`.http.award.controller

import com.aboutme.adapter.`in`.http.award.controller.api.AwardApi
import com.aboutme.adapter.`in`.http.award.req.AwardSyncReq
import com.aboutme.app.award.port.`in`.AwardUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class AwardController(
    private val awardUseCase: AwardUseCase,
) : AwardApi {
    @PutMapping("/awards")
    override fun sync(
        @RequestBody @Validated reqs: List<AwardSyncReq>,
    ) {
        awardUseCase.sync(reqs.map(AwardSyncReq::toCommand))
    }

    @GetMapping("/awards")
    override fun readDetails() = awardUseCase.readDetails()

    @DeleteMapping("/awards")
    override fun deleteAll() = awardUseCase.deleteAll()
}
