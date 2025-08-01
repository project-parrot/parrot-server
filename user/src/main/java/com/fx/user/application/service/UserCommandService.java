package com.fx.user.application.service;

import com.fx.user.application.port.in.UserCommandUseCase;
import com.fx.user.application.port.in.dto.UserSignUpCommand;
import com.fx.user.application.port.out.ProfilePersistencePort;
import com.fx.user.application.port.out.UserPersistencePort;
import com.fx.user.domain.Profile;
import com.fx.user.domain.User;
import com.fx.user.exception.UserException;
import com.fx.user.exception.errorcode.UserErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserCommandService implements UserCommandUseCase {

    private final UserPersistencePort userPersistencePort;
    private final ProfilePersistencePort profilePersistencePort;

    @Override
    @Transactional
    public User signUp(UserSignUpCommand signUpCommand) {

        // email, nickname 존재시 예외
        if (userPersistencePort.existsByEmail(signUpCommand.email())) {
            throw new UserException(UserErrorCode.EMAIL_EXISTS);
        }

        if (profilePersistencePort.existsByNickname(signUpCommand.nickname())) {
            throw new UserException(UserErrorCode.NICKNAME_EXISTS);
        }

        // 등록
        User savedUser = userPersistencePort.save(User.createUser(signUpCommand));
        profilePersistencePort.save(Profile.createProfile(savedUser.getId(), signUpCommand.nickname()));
        return savedUser;
    }

}
