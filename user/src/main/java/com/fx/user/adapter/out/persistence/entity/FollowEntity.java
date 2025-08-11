package com.fx.user.adapter.out.persistence.entity;

import com.fx.user.domain.FollowStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@Table(name = "follow")
@SuperBuilder
@NoArgsConstructor
public class FollowEntity extends BaseEntity {

    private Long followerId;

    private Long followingId;

    private FollowStatus status;

}
