package com.fx.post.adapter.out.persistence.entity;

import com.fx.post.domain.Like;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@Table(name = "likes",
        indexes = {
                @Index(name = "idx_likes_postid_userid", columnList = "post_id, user_id"),
                @Index(name = "idx_likes_postid", columnList = "post_id")
        })
@SuperBuilder
@NoArgsConstructor
public class LikeEntity extends BaseEntity {

    private Long userId;

    private Long postId;

    public static LikeEntity from(Like like) {
        return LikeEntity.builder()
                .userId(like.getUserId())
                .postId(like.getPostId())
                .build();
    }

    public Like toDomain() {
        return new Like(
                this.id,
                this.userId,
                this.postId,
                this.createdAt,
                this.updatedAt
        );
    }
}
