package com.aboutme.domain.persistence.resume.entity

import com.aboutme.domain.persistence.common.entity.SoftDeletableEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Table(name = "cover_letter")
@Entity
class CoverLetter(
    title: String,
    content: String,
) : SoftDeletableEntity() {
    var title: String = title
        protected set

    var content: String = content
        protected set
}
