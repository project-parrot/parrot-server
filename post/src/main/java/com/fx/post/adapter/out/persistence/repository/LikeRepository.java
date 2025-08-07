package com.fx.post.adapter.out.persistence.repository;

import com.fx.post.adapter.out.persistence.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    Boolean existsByPostIdAndUserId(Long postId, Long userId);
}
