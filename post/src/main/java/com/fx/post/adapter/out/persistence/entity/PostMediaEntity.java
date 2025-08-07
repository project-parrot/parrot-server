package com.fx.post.adapter.out.persistence.entity;

import com.fx.post.domain.PostMedia;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@Table(name = "post_media")
@SuperBuilder
@NoArgsConstructor
public class PostMediaEntity extends BaseEntity {
    private Long postId;

    private Long mediaId;

    public static PostMediaEntity from(PostMedia postMedia) {
        return PostMediaEntity.builder()
                .postId(postMedia.getPostId())
                .mediaId(postMedia.getMediaId())
                .build();
    }

    public PostMedia toDomain() {
        return new PostMedia(
                this.id,
                this.postId,
                this.mediaId,
                this.createdAt,
                this.updatedAt
        );
    }
}
