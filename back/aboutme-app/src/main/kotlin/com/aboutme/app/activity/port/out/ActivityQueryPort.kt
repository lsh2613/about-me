package com.aboutme.app.activity.port.out

import com.aboutme.core.activity.domain.Activity

interface ActivityQueryPort {
    fun loadAll(): List<Activity>
}
