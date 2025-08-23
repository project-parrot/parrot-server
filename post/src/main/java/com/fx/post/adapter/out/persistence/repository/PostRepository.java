package com.fx.post.adapter.out.persistence.repository;

import com.fx.post.adapter.out.persistence.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Boolean existsByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

    Optional<PostEntity> findByIdAndIsDeletedNot(Long id, Boolean isDeleted);

}
