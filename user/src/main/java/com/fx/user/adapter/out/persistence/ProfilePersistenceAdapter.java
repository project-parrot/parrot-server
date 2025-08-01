package com.fx.user.adapter.out.persistence;

import com.fx.global.annotation.hexagonal.PersistenceAdapter;
import com.fx.user.adapter.out.persistence.entity.ProfileEntity;
import com.fx.user.adapter.out.persistence.repository.ProfileRepository;
import com.fx.user.application.port.out.ProfilePersistencePort;
import com.fx.user.domain.Profile;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class ProfilePersistenceAdapter implements ProfilePersistencePort {

    private final ProfileRepository profileRepository;

    @Override
    public Profile save(Profile profile) {
        return profileRepository.save(ProfileEntity.from(profile)).toDomain();
    }

    @Override
    public Boolean existsByNickname(String nickname) {
        return profileRepository.existsByNickname(nickname);
    }

}
