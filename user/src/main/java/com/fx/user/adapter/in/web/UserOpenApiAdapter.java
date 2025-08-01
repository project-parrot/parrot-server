package com.fx.user.adapter.in.web;

import com.fx.global.annotation.hexagonal.WebAdapter;
import com.fx.global.api.Api;
import com.fx.user.adapter.in.web.dto.UserIdResponse;
import com.fx.user.adapter.in.web.dto.UserSignUpRequest;
import com.fx.user.application.port.in.UserCommandUseCase;
import com.fx.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@WebAdapter
@RequiredArgsConstructor
@RequestMapping("/open-api/v1/user")
public class UserOpenApiAdapter {

    private final UserCommandUseCase userCommandUseCase;

    @PostMapping("/signup")
    public ResponseEntity<Api<UserIdResponse>> signUp(@RequestBody UserSignUpRequest signUpRequest) {
        User user = userCommandUseCase.signUp(signUpRequest.toCommand(signUpRequest));
        return Api.OK(new UserIdResponse(user.getId()));
    }

}
