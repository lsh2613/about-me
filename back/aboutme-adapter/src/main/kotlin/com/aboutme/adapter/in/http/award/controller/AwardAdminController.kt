package com.aboutme.adapter.`in`.http.award.controller

import com.aboutme.adapter.common.annotation.AdminController
import com.aboutme.adapter.`in`.http.award.controller.api.AwardAdminApi
import com.aboutme.adapter.`in`.http.award.req.AwardSyncReq
import com.aboutme.app.award.port.`in`.AwardUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@AdminController
class AwardAdminController(
    private val awardUseCase: AwardUseCase,
) : AwardAdminApi {
    @PutMapping("/awards")
    override fun sync(
        @RequestBody @Validated reqs: List<AwardSyncReq>,
    ) {
        awardUseCase.sync(reqs.map(AwardSyncReq::toCommand))
    }

    @DeleteMapping("/awards")
    override fun deleteAll() = awardUseCase.deleteAll()
}
