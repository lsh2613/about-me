package com.aboutme.domain.persistence.resume.entity

import com.aboutme.domain.persistence.common.converter.StringListConverter
import com.aboutme.domain.persistence.common.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Table(name = "instruction")
@Entity
class Instruction(
    profileImageUrl: String,
    val name: String,
    emails: List<String>,
    region: String,
    education: String,
    skills: List<String>,
) : BaseEntity() {
    var profileImageUrl: String = profileImageUrl
        protected set

    @Convert(converter = StringListConverter::class)
    @Column(columnDefinition = "VARCHAR")
    var emails: List<String> = emails
        protected set
    var region: String = region
        protected set
    var education: String = education
        protected set

    @Convert(converter = StringListConverter::class)
    @Column(columnDefinition = "VARCHAR")
    var skills: List<String> = skills
        protected set
}
