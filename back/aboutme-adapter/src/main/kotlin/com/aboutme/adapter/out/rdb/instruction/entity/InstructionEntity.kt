package com.aboutme.adapter.out.rdb.instruction.entity

import com.aboutme.adapter.out.rdb.common.converter.StringListConverter
import com.aboutme.adapter.out.rdb.common.entity.DateAuditable
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "instruction")
@Entity
class InstructionEntity(
    profileImageUrl: String? = null,
    name: String,
    emails: List<String>,
    region: String,
    education: String,
    skills: List<String>,
) : DateAuditable() {
    fun update(
        name: String,
        emails: List<String>,
        region: String,
        education: String,
        skills: List<String>,
        profileImageUrl: String?,
    ) {
        this.name = name
        this.emails = emails
        this.profileImageUrl = profileImageUrl
        this.region = region
        this.education = education
        this.skills = skills
    }

    fun updateProfile(profileImageUrl: String? = null) {
        this.profileImageUrl = profileImageUrl
    }

    @Id
    val id: Long = 0L

    var profileImageUrl: String? = profileImageUrl
        protected set
    var name: String = name
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
