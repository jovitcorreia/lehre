package com.lehre.authuser.services;

import com.lehre.authuser.models.UserModel;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
  List<UserModel> findAll();

  Optional<UserModel> findById(UUID id);

  @Transactional
  void delete(UserModel userModel);

  @Transactional
  void save(UserModel userModel);

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);
}
