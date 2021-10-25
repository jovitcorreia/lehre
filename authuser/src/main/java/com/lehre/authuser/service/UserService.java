package com.lehre.authuser.service;

import com.lehre.authuser.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
  Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable);

  Optional<UserModel> findById(UUID id);

  @Transactional
  void delete(UserModel userModel);

  @Transactional
  void save(UserModel userModel);

  boolean existsByEmail(String email);

  boolean existsByUsername(String username);
}
