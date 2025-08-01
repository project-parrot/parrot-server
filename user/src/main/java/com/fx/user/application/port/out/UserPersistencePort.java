package com.fx.user.application.port.out;

import com.fx.user.domain.User;

public interface UserPersistencePort {

    User save(User user);

    Boolean existsByEmail(String email);

}
