package com.fx.user.application.port.out;

import com.fx.user.domain.Profile;

public interface ProfilePersistencePort {

    Profile save(Profile profile);

    Boolean existsByNickname(String nickname);

}
