package com.lehre.authuser.service.impl;

import com.lehre.authuser.model.UserModel;
import com.lehre.authuser.repository.UserRepository;
import com.lehre.authuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;

  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public Page<UserModel> findAll(Specification<UserModel> spec, Pageable pageable) {
    return userRepository.findAll(spec, pageable);
  }

  @Override
  public Optional<UserModel> find(UUID id) {
    return userRepository.findById(id);
  }

  @Override
  public void delete(UserModel userModel) {
    userRepository.delete(userModel);
  }

  @Override
  public void save(UserModel userModel) {
    userRepository.save(userModel);
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }
}
