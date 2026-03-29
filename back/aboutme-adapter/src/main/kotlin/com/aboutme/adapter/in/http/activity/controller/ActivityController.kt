package com.aboutme.adapter.`in`.http.activity.controller

import com.aboutme.adapter.`in`.http.activity.controller.api.ActivityApi
import com.aboutme.app.activity.port.`in`.ActivityUseCase
import com.aboutme.app.activity.service.dto.rep.ActivityDetailRep
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ActivityController(
    private val activityUseCase: ActivityUseCase,
) : ActivityApi {
    @GetMapping("/activities")
    override fun readDetails(): List<ActivityDetailRep> = activityUseCase.readDetails()
}
