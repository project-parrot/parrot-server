package com.fx.user.domain;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Profile {

    private Long id;

    private Long userId;

    private Long mediaId;

    private String nickname;

    private String statusMessage;

    private Boolean isPrivate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static Profile createProfile(Long userId, String nickname) {
        return Profile.builder()
            .userId(Objects.requireNonNull(userId))
            .nickname(Objects.requireNonNull(nickname))
            .isPrivate(false)
            .build();
    }

}
