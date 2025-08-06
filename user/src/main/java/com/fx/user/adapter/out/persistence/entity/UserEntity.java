package com.fx.user.adapter.out.persistence.entity;

import com.fx.global.dto.user.UserRole;
import com.fx.user.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private String oauthId;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public static UserEntity fromDomain(User user) {
        return UserEntity.builder()
            .email(user.getEmail())
            .password(user.getPassword())
            .phone(user.getPhone())
            .oauthId(user.getOauthId())
            .role(user.getRole())
            .build();
    }

    public User toDomain() {
        return new User(
            this.getId(),
            this.email,
            this.password,
            this.phone,
            this.oauthId,
            this.role,
            this.createdAt,
            this.updatedAt
        );
    }

}