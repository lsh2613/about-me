package com.aboutme.adapter.`in`.http.activity.controller

import com.aboutme.adapter.`in`.http.activity.controller.api.ActivityApi
import com.aboutme.adapter.`in`.http.activity.req.ActivitySyncReq
import com.aboutme.app.activity.port.`in`.ActivityUseCase
import com.aboutme.app.activity.service.dto.rep.ActivityDetailRep
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ActivityController(
    private val activityUseCase: ActivityUseCase,
) : ActivityApi {
    @PutMapping("/activities")
    override fun sync(
        @RequestBody reqs: List<ActivitySyncReq>,
    ) {
        activityUseCase.sync(reqs.map(ActivitySyncReq::toCommand))
    }

    @GetMapping("/activities")
    override fun readDetails(): List<ActivityDetailRep> = activityUseCase.readDetails()

    @DeleteMapping("/activities")
    override fun deleteAll() = activityUseCase.deleteAll()
}
