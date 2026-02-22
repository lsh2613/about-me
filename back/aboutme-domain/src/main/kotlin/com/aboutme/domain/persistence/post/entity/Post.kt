package com.aboutme.domain.persistence.post.entity

import com.aboutme.domain.persistence.common.entity.SoftDeletableEntity
import com.aboutme.domain.persistence.project.entity.Project
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Table(name = "post")
@Entity
class Post(
    title: String,
    content: String,
    project: Project?,
) : SoftDeletableEntity() {
    var title: String = title
        protected set

    @Column(columnDefinition = "TEXT")
    var content: String = content
        protected set

    @ManyToOne
    var project: Project? = project
        protected set
}
