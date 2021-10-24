package com.lehre.authuser.controllers;

import com.lehre.authuser.models.UserModel;
import com.lehre.authuser.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  public ResponseEntity<List<UserModel>> getAllUsers() {
    return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
  }
}
