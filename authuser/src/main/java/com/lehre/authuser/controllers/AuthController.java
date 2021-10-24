package com.lehre.authuser.controllers;

import com.lehre.authuser.dtos.UserSummary;
import com.lehre.authuser.mappers.UserMapper;
import com.lehre.authuser.models.UserModel;
import com.lehre.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth")
public class AuthController {
  private final UserService userService;

  @Autowired
  public AuthController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/signup")
  public ResponseEntity<UserModel> registerUser(@RequestBody UserSummary userSummary) {
    UserModel userModel = UserMapper.newStudent(userSummary);
    userService.save(userModel);
    URI location = URI.create(String.format("/users/%s", userSummary.getId()));
    return ResponseEntity.created(location).build();
  }
}
