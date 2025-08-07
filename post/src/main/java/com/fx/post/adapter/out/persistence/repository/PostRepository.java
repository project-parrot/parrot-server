package com.fx.post.adapter.out.persistence.repository;

import com.fx.post.adapter.out.persistence.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Boolean existsByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);
}
