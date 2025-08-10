package com.fx.post.adapter.out.persistence.repository;

import com.fx.post.adapter.out.persistence.entity.LikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LikeRepository extends JpaRepository<LikeEntity, Long> {

    Boolean existsByPostIdAndUserId(Long postId, Long userId);

    @Modifying
    @Query("DELETE FROM LikeEntity l WHERE l.postId = :postId AND l.userId = :userId")
    Integer deleteByPostIdAndUserId(@Param("postId") Long postId, @Param("userId") Long userId);

    List<LikeEntity> findByPostId(Long postId);
}
