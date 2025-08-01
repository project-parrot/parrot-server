package com.fx.user.domain;

import com.fx.user.application.port.in.dto.UserSignUpCommand;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class User {

    private Long id;

    private String email;

    private String password;

    private String phone;

    private String OAuthProvider;

    private Long OAuthId;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static User createUser(UserSignUpCommand signUpCommand) {
        return User.builder()
            .email(signUpCommand.email())
            .password(signUpCommand.password())
            .phone(signUpCommand.phone())
            .build();
    }

}
