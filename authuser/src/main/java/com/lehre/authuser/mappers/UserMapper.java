package com.lehre.authuser.mappers;

import com.lehre.authuser.dtos.UserData;
import com.lehre.authuser.enums.UserStatus;
import com.lehre.authuser.enums.UserType;
import com.lehre.authuser.models.UserModel;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class UserMapper {
  public static UserModel newUser(UserData userData) {
    return UserModel.builder()
        .username(userData.getUsername())
        .email(userData.getEmail())
        .password(userData.getPassword())
        .fullName(userData.getFullName())
        .status(UserStatus.ACTIVE)
        .type(UserType.STUDENT)
        .phoneNumber(userData.getPhoneNumber())
        .cpf(userData.getCpf())
        .imageUrl(userData.getImageUrl())
        .creationDate(LocalDateTime.now(ZoneId.of("UTC")))
        .lastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")))
        .build();
  }
}
