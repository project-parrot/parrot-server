package com.fx.user.adapter.out.persistence.entity;

import com.fx.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Entity
@Table(name = "user")
@SuperBuilder
@NoArgsConstructor
public class UserEntity extends BaseEntity{

    private String email;

    private String password;

    private String phone;

    private String OAuthProvider;

    private Long OAuthId;

    public static UserEntity fromDomain(User user) {
        return UserEntity.builder()
            .email(user.getEmail())
            .password(user.getPassword())
            .phone(user.getPhone())
            .build();
    }

    public User toDomain() {
        return User.builder()
            .id(this.id)
            .email(this.email)
            .password(this.password)
            .phone(this.phone)
            .OAuthProvider(this.OAuthProvider)
            .OAuthId(this.OAuthId)
            .build();
    }

}