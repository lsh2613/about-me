package com.aboutme.adapter.`in`.http.essay.controller

import com.aboutme.adapter.common.annotation.AdminController
import com.aboutme.adapter.`in`.http.essay.controller.api.EssayAdminApi
import com.aboutme.adapter.`in`.http.essay.req.EssaySyncReq
import com.aboutme.app.essay.port.`in`.EssayUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@AdminController
class EssayAdminController(
    private val essayUseCase: EssayUseCase,
) : EssayAdminApi {
    @PutMapping("/essays")
    override fun sync(
        @RequestBody @Validated reqs: List<EssaySyncReq>,
    ) {
        essayUseCase.sync(reqs.map { it.toCommand() })
    }

    @DeleteMapping("/essays")
    override fun deleteAll() = essayUseCase.deleteAll()
}
