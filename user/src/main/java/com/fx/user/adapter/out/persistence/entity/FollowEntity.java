package com.fx.user.adapter.out.persistence.entity;

import com.fx.user.domain.Follow;
import com.fx.user.domain.FollowStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.jetbrains.annotations.NotNull;

@Getter
@Entity
@Table(name = "follow")
@SuperBuilder
@NoArgsConstructor
public class FollowEntity extends BaseEntity {

    private Long followerId;

    private Long followingId;

    @Enumerated(EnumType.STRING)
    private FollowStatus status;

    public static FollowEntity fromDomain(Follow follow) {
        return FollowEntity.builder()
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
