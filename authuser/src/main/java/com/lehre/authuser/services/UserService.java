package com.lehre.authuser.services;

import com.lehre.authuser.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
  Page<UserModel> findAll(Pageable pageable);

  Optional<UserModel> findById(UUID id);

  @Transactional
  void delete(UserModel userModel);

  @Transactional
  void save(UserModel userModel);

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);
}
