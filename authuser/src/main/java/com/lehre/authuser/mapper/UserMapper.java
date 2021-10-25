package com.lehre.authuser.mapper;

import com.lehre.authuser.constant.UserStatus;
import com.lehre.authuser.constant.UserType;
import com.lehre.authuser.data.UserData;
import com.lehre.authuser.model.UserModel;
import com.lehre.authuser.util.Mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class UserMapper implements Mapper<UserData, UserModel> {
  @Override
  public UserModel from(UserData userData) {
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
