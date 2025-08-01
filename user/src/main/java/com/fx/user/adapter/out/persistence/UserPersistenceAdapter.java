package com.fx.user.adapter.out.persistence;

import com.fx.global.annotation.hexagonal.PersistenceAdapter;
import com.fx.user.adapter.out.persistence.entity.UserEntity;
import com.fx.user.adapter.out.persistence.repository.UserRepository;
import com.fx.user.application.port.out.UserPersistencePort;
import com.fx.user.domain.User;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserRepository userRepository;

    @Override
    public User save(User user) {
        return  userRepository.save(UserEntity.fromDomain(user)).toDomain();
    }

    @Override
    public Boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
