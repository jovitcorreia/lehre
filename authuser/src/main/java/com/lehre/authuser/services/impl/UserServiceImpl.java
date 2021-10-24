package com.lehre.authuser.services.impl;

import com.lehre.authuser.models.UserModel;
import com.lehre.authuser.repositories.UserRepository;
import com.lehre.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
  public List<UserModel> findAll() {
    return userRepository.findAll();
  }

  @Override
  public Optional<UserModel> findById(UUID id) {
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
