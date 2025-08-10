package com.fx.post.adapter.out.persistence.repository;

import com.fx.post.adapter.out.persistence.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByPostIdOrderByCreatedAtAsc(Long postId);

    List<CommentEntity> findByUserIdOrderByCreatedAtDesc(Long userId);
}
