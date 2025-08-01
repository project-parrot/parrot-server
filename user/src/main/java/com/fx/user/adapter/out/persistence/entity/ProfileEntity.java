package com.fx.user.adapter.out.persistence.entity;

import com.fx.user.domain.Profile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;

@Getter
@Entity
@Table(name = "profile")
@SuperBuilder
@NoArgsConstructor
public class ProfileEntity  extends BaseEntity {

    private Long userId;

    private Long mediaId;

    private String nickname;

    private String statusMessage;

    @Column(columnDefinition = "TINYINT(1)")
    private Boolean isPrivate;

    public static ProfileEntity from(Profile profile) {
        return ProfileEntity.builder()
            .userId(profile.getUserId())
            .mediaId(profile.getMediaId())
            .nickname(profile.getNickname())
            .statusMessage(profile.getStatusMessage())
            .isPrivate(profile.getIsPrivate())
            .build();
    }

    public Profile toDomain() {
        return Profile.builder()
            .id(this.id)
            .userId(this.userId)
            .nickname(this.nickname)
            .statusMessage(this.statusMessage)
            .isPrivate(this.isPrivate)
            .build();
    }

}
