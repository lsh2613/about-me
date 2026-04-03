package com.aboutme.adapter.out.rdb.post.entity

import com.aboutme.adapter.out.rdb.common.converter.StringListConverter
import com.aboutme.adapter.out.rdb.common.entity.SoftDeletableEntity
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.SQLDelete

@SQLDelete(sql = "UPDATE post SET deleted_at = NOW() WHERE id = ?")
@Table(name = "post")
@Entity
class PostEntity(
    id: Long? = null,
    title: String? = null,
    content: String? = null,
    references: List<String>? = null,
) : SoftDeletableEntity(id) {
    var title: String? = title
        protected set

    @Column(columnDefinition = "TEXT")
    var content: String? = content
        protected set

    @Convert(converter = StringListConverter::class)
    @Column(columnDefinition = "VARCHAR")
    var referenceUrls: List<String>? = references

    fun update(
        title: String?,
        content: String?,
        references: List<String>?,
    ) {
        this.title = title
        this.content = content
        this.referenceUrls = references
    }
}
