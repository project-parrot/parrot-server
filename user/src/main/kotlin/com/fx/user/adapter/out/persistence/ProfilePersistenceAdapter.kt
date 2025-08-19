package com.fx.user.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.user.adapter.out.persistence.entity.ProfileEntity
import com.fx.user.adapter.out.persistence.repository.ProfileRepository
import com.fx.user.application.out.ProfilePersistencePort
import com.fx.user.domain.Profile

@PersistenceAdapter
class ProfilePersistenceAdapter(
    private val profileRepository: ProfileRepository
) : ProfilePersistencePort {

    override fun save(profile: Profile): Profile =
        profileRepository.save(ProfileEntity.from(profile)).toDomain()

    override fun existsByNickname(nickname: String): Boolean =
        profileRepository.existsByNickname(nickname)

    override fun findByUserId(userId: Long): Profile? =
        profileRepository.findByUserId(userId).orElse(null)?.toDomain()

    override fun updateProfile(profile: Profile): Profile =
        profileRepository.save(ProfileEntity.from(profile)).toDomain()

    override fun findByUserIdIn(userIds: List<Long>): List<Profile> =
        profileRepository.findByUserIdIn(userIds).map { it.toDomain() }
}