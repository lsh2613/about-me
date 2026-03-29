package com.aboutme.adapter.`in`.http.profile.controller

import com.aboutme.adapter.`in`.http.profile.controller.api.ProfileApi
import com.aboutme.app.profile.port.`in`.ProfileUseCase
import com.aboutme.app.profile.service.dto.rep.ProfileDetailRep
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ProfileController(
    private val profileUseCase: ProfileUseCase,
) : ProfileApi {
    @GetMapping("/profiles")
    override fun readDetail(): ProfileDetailRep {
        return profileUseCase.readDetail()
    }
}
