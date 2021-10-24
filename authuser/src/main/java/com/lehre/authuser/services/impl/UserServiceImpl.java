package com.lehre.authuser.services.impl;

import com.lehre.authuser.models.UserModel;
import com.lehre.authuser.repositories.UserRepository;
import com.lehre.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
