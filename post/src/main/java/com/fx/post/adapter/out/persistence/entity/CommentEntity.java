package com.fx.post.adapter.out.persistence.entity;

import com.fx.post.domain.Comment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@Table(name = "comment",
        indexes = {
        @Index(name = "idx_comment_postid_createdat", columnList = "post_id, created_at"),
        @Index(name = "idx_comment_userid_createdat", columnList = "user_id, created_at"),
})
@SuperBuilder
@NoArgsConstructor
public class CommentEntity extends BaseEntity {

    private Long userId;

    private Long postId;

    private Long parentId;

    @Column(nullable = false)
    private String content;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isDeleted;

    public static CommentEntity from(Comment comment) {
        return CommentEntity.builder()
                .userId(comment.getUserId())
                .postId(comment.getPostId())
                .parentId(comment.getParentId())
                .content(comment.getContent())
                .isDeleted(comment.isDeleted())
                .build();
    }

    public Comment toDomain() {
        return new Comment(
                this.id,
                this.userId,
                this.postId,
                this.parentId,
                this.content,
                this.isDeleted,
                this.createdAt,
                this.updatedAt
        );
    }
}
