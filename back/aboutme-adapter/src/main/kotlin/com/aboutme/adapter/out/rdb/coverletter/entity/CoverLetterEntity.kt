package com.aboutme.adapter.out.rdb.coverletter.entity

import com.aboutme.adapter.out.rdb.common.entity.SoftDeletableEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Table(name = "cover_letter")
@Entity
class CoverLetterEntity(
    title: String,
    content: String,
) : SoftDeletableEntity() {
    var title: String = title
        protected set

    var content: String = content
        protected set
}
