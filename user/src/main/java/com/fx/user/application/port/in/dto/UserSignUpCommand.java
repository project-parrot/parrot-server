package com.fx.user.application.port.in.dto;

public record UserSignUpCommand(
    String email,
    String password,
    String phone,
    String nickname
) {

}
