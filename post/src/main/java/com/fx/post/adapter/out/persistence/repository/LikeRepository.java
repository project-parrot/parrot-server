package com.fx.post.adapter.out.persistence.repository;

import com.fx.post.adapter.out.persistence.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    Boolean existsByPostIdAndUserId(Long postId, Long userId);

    Optional<LikeEntity> findByPostIdAndUserId(Long postId, Long userId);

    void deleteByPostIdAndUserId(Long postId, Long userId);
}
