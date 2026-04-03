package com.aboutme.adapter.out.rdb.project

import com.aboutme.adapter.common.annotation.Adapter
import com.aboutme.adapter.out.rdb.project.repository.ProjectJpaRepository
import com.aboutme.app.project.port.out.ProjectCommandPort
import com.aboutme.app.project.port.out.ProjectQueryPort

@Adapter
class ProjectAdapter(
    private val projectJpaRepository: ProjectJpaRepository,
) : ProjectCommandPort, ProjectQueryPort
