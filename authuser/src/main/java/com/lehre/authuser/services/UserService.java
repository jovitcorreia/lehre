package com.lehre.authuser.services;

import com.lehre.authuser.models.UserModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
  List<UserModel> findAll();

  Optional<UserModel> findById(UUID id);

  void delete(UserModel userModel);
}
