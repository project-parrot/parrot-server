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
    SELECT new com.fx.post.adapter.out.persistence.dto.PostSummaryDto(p.id, p.userId, p.content, p.createdAt, 
        COUNT(DISTINCT l.id), COUNT(DISTINCT c.id))
    FROM PostEntity p
    LEFT JOIN LikeEntity l ON p.id = l.postId
    LEFT JOIN CommentEntity c ON p.id = c.postId
    WHERE p.userId IN :userIds
        AND p.id < :postId
        AND p.isDeleted = :isDeleted
    GROUP BY p.id, p.userId, p.content, p.createdAt
    ORDER BY p.id DESC
    LIMIT 10
    """
    )
    List<PostSummaryDto> findByUserIdAndPostIdBeforeAndIsDeleted(@Param("userIds") List<Long> userIds, @Param("postId") Long postId, @Param("isDeleted") Boolean isDeleted);

    @Query("""
    SELECT new com.fx.post.adapter.out.persistence.dto.PostSummaryDto(
            p.id, p.userId, p.content, p.createdAt,
            count(distinct l.id), count(distinct c.id)
        )
        from PostEntity p
        left join LikeEntity l on l.postId = p.id
        left join CommentEntity c on c.postId = p.id
        where l.userId = :userId and p.createdAt < :before and p.isDeleted = :isDeleted
        group by p.id, p.userId, p.content, p.createdAt
        order by p.createdAt desc
        limit 10
    """)
    List<PostSummaryDto> findLikedPostsByUserIdAndCreatedAtBeforeAndIsDeleted(@Param("userId") Long userId, @Param("before") LocalDateTime before, @Param("isDeleted") Boolean isDeleted);
}
