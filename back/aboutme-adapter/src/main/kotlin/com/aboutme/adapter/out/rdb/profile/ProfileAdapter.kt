package com.aboutme.adapter.out.rdb.profile

import com.aboutme.adapter.common.annotation.Adapter
import com.aboutme.adapter.out.rdb.profile.entity.ProfileEntity
import com.aboutme.adapter.out.rdb.profile.mapper.ProfileMapper
import com.aboutme.adapter.out.rdb.profile.repository.ProfileJpaRepository
import com.aboutme.app.profile.port.out.ProfileCommandPort
import com.aboutme.app.profile.port.out.ProfileQueryPort
import com.aboutme.common.exception.GlobalException
import com.aboutme.core.profile.domain.Profile
import com.aboutme.core.profile.error.ProfileErrorCode
import org.springframework.transaction.annotation.Transactional

@Adapter
class ProfileAdapter(
    private val profileJpaRepository: ProfileJpaRepository,
) : ProfileCommandPort, ProfileQueryPort {
    companion object {
        const val SINGLETON_ID = 0L
    }

    @Transactional
    override fun save(profile: Profile) {
        profileJpaRepository.save(ProfileMapper.toEntity(profile))
    }

    @Transactional(readOnly = true)
    override fun findOrNull(): Profile? {
        return profileJpaRepository.findById(SINGLETON_ID).orElse(null)
            ?.let(ProfileMapper::toDomain)
    }

    @Transactional(readOnly = true)
    override fun findOrThrow(): Profile {
        return findEntityByIdOrThrow().let(ProfileMapper::toDomain)
    }

    @Transactional
    override fun update(profile: Profile) {
        findEntityByIdOrThrow().apply {
            update(
                name = profile.name,
                emails = profile.emails.map { it.value },
                region = profile.region,
                education = profile.education,
                skills = profile.skills,
                profileImagePath = profile.profileImagePath,
            )
        }
    }

    @Transactional
    override fun updateProfile(path: String) {
        findEntityByIdOrThrow().apply {
            updateProfile(path)
        }
    }

    @Transactional
    override fun deleteProfile() {
        findEntityByIdOrThrow().apply {
            updateProfile()
        }
    }

    private fun findEntityByIdOrThrow(): ProfileEntity =
        profileJpaRepository.findById(SINGLETON_ID)
            .orElseThrow { throw GlobalException(ProfileErrorCode.NOT_FOUND) }
}
