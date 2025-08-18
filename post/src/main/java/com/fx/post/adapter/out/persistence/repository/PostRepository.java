package com.fx.post.adapter.out.persistence.repository;

import com.fx.post.adapter.out.persistence.dto.PostSummaryDto;
import com.fx.post.adapter.out.persistence.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<PostEntity, Long> {

    Boolean existsByUserIdAndCreatedAtBetween(Long userId, LocalDateTime start, LocalDateTime end);

    Optional<PostEntity> findByIdAndIsDeletedNot(Long id, Boolean isDeleted);

    @Query("""
    SELECT new com.fx.post.adapter.out.persistence.dto.PostSummaryDto(
            p.id, p.userId, p.content, p.createdAt,
            count(distinct l.id), count(distinct c.id)
        )
    FROM PostEntity p
    LEFT JOIN LikeEntity l ON l.postId = p.id
    LEFT JOIN CommentEntity c ON c.postId = p.id
    WHERE l.userId = :userId
        AND p.id < :postId
        AND p.isDeleted = :isDeleted
    GROUP BY p.id, p.userId, p.content, p.createdAt
    ORDER BY p.id DESC
    LIMIT 10
    """)
    List<PostSummaryDto> findLikedPostsByUserIdAndPostIdBeforeAndIsDeleted(@Param("userId") Long userId, @Param("postId") Long postId, @Param("isDeleted") Boolean isDeleted);
}
