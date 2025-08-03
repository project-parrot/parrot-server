package com.fx.user.adapter.out.persistence

import com.fx.global.annotation.hexagonal.PersistenceAdapter
import com.fx.user.adapter.out.persistence.entity.ProfileEntity
import com.fx.user.adapter.out.persistence.repository.ProfileRepository
import com.fx.user.application.out.ProfilePersistencePort
import com.fx.user.domain.Profile

@PersistenceAdapter
class ProfilePersistenceAdapterKotlin(
    private val profileRepository: ProfileRepository
) : ProfilePersistencePort {

    override fun save(profile: Profile): Profile {
        return profileRepository.save(ProfileEntity.from(profile)).toDomain()
    }

    override fun existsByNickname(nickname: String): Boolean {
        return profileRepository.existsByNickname(nickname);
    }
}