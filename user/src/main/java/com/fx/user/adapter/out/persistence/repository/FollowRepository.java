package com.fx.user.adapter.out.persistence.repository;

import com.fx.user.adapter.out.persistence.entity.FollowEntity;
import com.fx.user.domain.FollowStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<FollowEntity, Long> {

    Integer countByFollowerIdAndStatus(Long userId, FollowStatus status);

    Integer countByFollowingIdAndStatus(Long userId, FollowStatus status);

    Boolean existsByFollowerIdAndFollowingIdAndStatus(Long followerId, Long followingId, FollowStatus status);

}
