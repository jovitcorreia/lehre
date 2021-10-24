package com.lehre.authuser.mappers;

import com.lehre.authuser.dtos.UserSummary;
import com.lehre.authuser.enums.UserStatus;
import com.lehre.authuser.enums.UserType;
import com.lehre.authuser.models.UserModel;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class UserMapper {
  public static UserModel newUser(UserSummary userSummary) {
    return UserModel.builder()
        .username(userSummary.getUsername())
        .email(userSummary.getEmail())
        .password(userSummary.getPassword())
        .fullName(userSummary.getFullName())
        .status(UserStatus.ACTIVE)
        .type(UserType.STUDENT)
        .phoneNumber(userSummary.getPhoneNumber())
        .cpf(userSummary.getCpf())
        .imageUrl(userSummary.getImageUrl())
        .creationDate(LocalDateTime.now(ZoneId.of("UTC")))
        .lastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")))
        .build();
  }
}
