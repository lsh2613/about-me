package com.aboutme.app.activity.port.out

import com.aboutme.core.activity.domain.Activity

interface ActivityCommandPort {
    fun save(activity: Activity)

    fun delete(ids: List<Long>)

    fun update(activity: Activity)

    fun deleteAll()
}
