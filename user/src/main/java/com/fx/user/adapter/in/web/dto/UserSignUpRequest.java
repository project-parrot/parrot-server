package com.fx.user.adapter.in.web.dto;

import com.fx.user.application.port.in.dto.UserSignUpCommand;

public record UserSignUpRequest(
    String email,
    String password,
    String phone,
    String nickname
) {
    public UserSignUpCommand toCommand(UserSignUpRequest signUpRequest) {
        return new UserSignUpCommand(signUpRequest.email, signUpRequest.password, signUpRequest.phone, signUpRequest.nickname);
    }
}