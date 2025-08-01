package com.fx.user.application.port.in;

import com.fx.user.application.port.in.dto.UserSignUpCommand;
import com.fx.user.domain.User;

public interface UserCommandUseCase {

    User signUp(UserSignUpCommand signUpCommand);

}
