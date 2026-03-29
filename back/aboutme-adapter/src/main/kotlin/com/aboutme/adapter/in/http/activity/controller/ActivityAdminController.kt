package com.aboutme.adapter.`in`.http.activity.controller

import com.aboutme.adapter.common.annotation.AdminController
import com.aboutme.adapter.`in`.http.activity.controller.api.ActivityAdminApi
import com.aboutme.adapter.`in`.http.activity.req.ActivitySyncReq
import com.aboutme.app.activity.port.`in`.ActivityUseCase
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@AdminController
class ActivityAdminController(
    private val activityUseCase: ActivityUseCase,
) : ActivityAdminApi {
    @PutMapping("/activities")
    override fun sync(
        @RequestBody @Validated reqs: List<ActivitySyncReq>,
    ) {
        activityUseCase.sync(reqs.map(ActivitySyncReq::toCommand))
    }

    @DeleteMapping("/activities")
    override fun deleteAll() = activityUseCase.deleteAll()
}
