package com.lehre.authuser.service;

import com.lehre.authuser.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
  @Transactional
  void store(UserModel userModel);

  Optional<UserModel> show(UUID id);

  Page<UserModel> index(Specification<UserModel> spec, Pageable pageable);

  @Transactional
  void delete(UserModel userModel);

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);
}
