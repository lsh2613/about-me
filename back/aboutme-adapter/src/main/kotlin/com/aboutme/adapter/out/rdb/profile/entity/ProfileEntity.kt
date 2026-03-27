package com.aboutme.adapter.out.rdb.profile.entity

import com.aboutme.adapter.out.rdb.common.converter.StringListConverter
import com.aboutme.adapter.out.rdb.common.entity.DateAuditable
import jakarta.persistence.Column
import jakarta.persistence.Convert
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "profile")
@Entity
class ProfileEntity(
    profileImagePath: String? = null,
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
        profileImagePath: String?,
    ) {
        this.name = name
        this.emails = emails
        this.profileImagePath = profileImagePath
        this.region = region
        this.education = education
        this.skills = skills
    }

    fun updateProfile(profileImagePath: String? = null) {
        this.profileImagePath = profileImagePath
    }

    @Id
    val id: Long = 0L

    var profileImagePath: String? = profileImagePath
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
