package com.fx.user.adapter.out.persistence.repository;

import com.fx.user.adapter.out.persistence.entity.ProfileEntity;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    Boolean existsByNickname(String nickname);

    Optional<ProfileEntity> findByUserId(Long userId);

    List<ProfileEntity> findByUserIdIn(List<Long> userIds);

}
