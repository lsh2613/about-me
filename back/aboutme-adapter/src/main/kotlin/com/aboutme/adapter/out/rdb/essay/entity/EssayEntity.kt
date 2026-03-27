package com.aboutme.adapter.out.rdb.essay.entity

import com.aboutme.adapter.out.rdb.common.entity.SoftDeletableEntity
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE essay SET deleted_at = NOW() WHERE id = ?")
@Table(name = "essay")
@Entity
class EssayEntity(
    title: String,
    content: String,
    seq: Int,
) : SoftDeletableEntity() {
    var title: String = title
        protected set

    var content: String = content
        protected set

    var seq: Int = seq

    fun update(
        title: String,
        content: String,
        seq: Int,
    ) {
        this.title = title
        this.content = content
        this.seq = seq
    }
}
