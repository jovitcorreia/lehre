package com.lehre.authuser.services;

import com.lehre.authuser.models.UserModel;

import java.util.List;

public interface UserService {
  List<UserModel> findAll();
}
