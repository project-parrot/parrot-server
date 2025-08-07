package com.fx.post.adapter.out.persistence.entity;

import com.fx.post.domain.Post;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@Table(name = "post")
@SuperBuilder
@NoArgsConstructor
public class PostEntity extends BaseEntity {

    private Long userId;

    private String content;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isDeleted;

    public static PostEntity from(Post post) {
        return PostEntity.builder()
                .userId(post.getUserId())
                .content(post.getContent())
                .isDeleted(post.isDeleted())
                .build();
    }

    public Post toDomain() {
        return new Post(
                this.id,
                this.userId,
                this.content,
                this.isDeleted,
                this.createdAt,
                this.updatedAt
        );
    }
}
