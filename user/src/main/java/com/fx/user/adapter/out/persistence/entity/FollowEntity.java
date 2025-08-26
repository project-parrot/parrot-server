package com.fx.user.adapter.out.persistence.entity;

import com.fx.user.domain.Follow;
import com.fx.user.domain.FollowStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@Table(name = "follow",
    indexes = {
        @Index(name = "idx_follower_status_id", columnList = "followerId, status, id"),
        @Index(name = "idx_following_status_id", columnList = "followingId, status, id"),
        @Index(name = "idx_follower_following_status", columnList = "followerId, followingId, status") // 특정 팔로우 존재 여부 확인 idx
    },
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_follower_following", columnNames = {"followerId", "followingId"})
    }
)
@SuperBuilder
@NoArgsConstructor
public class FollowEntity extends BaseEntity {

    private Long followerId;

    private Long followingId;

    @Enumerated(EnumType.STRING)
    private FollowStatus status;

    public static FollowEntity fromDomain(Follow follow) {
        return FollowEntity.builder()
            .id(follow.getId())
            .followerId(follow.getFollowerId())
            .followingId(follow.getFollowingId())
            .status(follow.getStatus())
            .build();
    }

    public Follow toDomain() {
        return new Follow(
            this.id,
            this.followerId,
            this.followingId,
            this.status,
            this.createdAt,
            this.updatedAt
        );
    }

}
