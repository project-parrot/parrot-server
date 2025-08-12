package com.fx.user.adapter.out.persistence.entity;

import com.fx.user.domain.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@Table(name = "profile")
@SuperBuilder
@NoArgsConstructor
public class ProfileEntity extends BaseEntity {

    private Long userId;

    private Long mediaId;

    private String nickname;

    private String statusMessage;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isPrivate;

    public static ProfileEntity from(Profile profile) {
        return ProfileEntity.builder()
            .id(profile.getId()) // nullable -> save / not null -> update
            .userId(profile.getUserId())
            .mediaId(profile.getMediaId())
            .nickname(profile.getNickname())
            .statusMessage(profile.getStatusMessage())
            .isPrivate(profile.isPrivate())
            .build();
    }

    public Profile toDomain() {
        return new Profile(
            this.id,
            this.userId,
            this.mediaId,
            this.nickname,
            this.statusMessage,
            this.isPrivate,
            this.createdAt,
            this.updatedAt
        );
    }
}
