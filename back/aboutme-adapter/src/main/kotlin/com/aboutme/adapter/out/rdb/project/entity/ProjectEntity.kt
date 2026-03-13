package com.aboutme.adapter.out.rdb.project.entity

import com.aboutme.adapter.out.rdb.common.converter.StringListConverter
import com.aboutme.adapter.out.rdb.common.entity.SoftDeletableEntity
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table(name = "project")
@Entity
class ProjectEntity(
    name: String,
    startedAt: LocalDateTime,
    endedAt: LocalDateTime?,
    skills: List<String>,
    summary: String,
    thumbnailUrl: String?,
    content: String,
) : SoftDeletableEntity() {
    var name: String = name
        protected set

    var startedAt: LocalDateTime = startedAt
        protected set

    var endedAt: LocalDateTime? = endedAt
        protected set

    @Convert(converter = StringListConverter::class)
    @Column(columnDefinition = "VARCHAR")
    var skills: List<String> = skills
        protected set

    var summary: String = summary
        protected set

    var thumbnailUrl: String? = thumbnailUrl
        protected set

    @Column(columnDefinition = "TEXT")
    var content: String = content
        protected set
}
