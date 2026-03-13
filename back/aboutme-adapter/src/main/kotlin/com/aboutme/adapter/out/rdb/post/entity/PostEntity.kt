package com.aboutme.adapter.out.rdb.post.entity

import com.aboutme.adapter.out.rdb.common.entity.SoftDeletableEntity
import com.aboutme.adapter.out.rdb.project.entity.ProjectEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Table(name = "post")
@Entity
class PostEntity(
    title: String,
    content: String,
    projectEntity: ProjectEntity?,
) : SoftDeletableEntity() {
    var title: String = title
        protected set

    @Column(columnDefinition = "TEXT")
    var content: String = content
        protected set

    @ManyToOne
    var projectEntity: ProjectEntity? = projectEntity
        protected set
}
