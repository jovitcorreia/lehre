package com.lehre.authuser.controllers;

import com.lehre.authuser.dtos.UserSummary;
import com.lehre.authuser.mappers.UserMapper;
import com.lehre.authuser.models.UserModel;
import com.lehre.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

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
  public ResponseEntity<?> registerUser(@RequestBody UserSummary userSummary) {
    if (userService.existsByUsername(userSummary.getUsername())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken!");
    } else if (userService.existsByEmail(userSummary.getEmail())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body(("Email is already taken!"));
    }
    UserModel userModel = UserMapper.newUser(userSummary);
    userService.save(userModel);
    return ResponseEntity.status(HttpStatus.CREATED).body(userModel);
  }
}
