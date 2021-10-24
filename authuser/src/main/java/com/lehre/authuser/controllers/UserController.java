package com.lehre.authuser.controllers;

import com.lehre.authuser.models.UserModel;
import com.lehre.authuser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUserById(@PathVariable UUID id) {
    Optional<UserModel> userModelOptional = userService.findById(id);
    if (userModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format("User with id %s not found!", id));
    }
    userService.delete(userModelOptional.get());
    return ResponseEntity.status(HttpStatus.OK)
        .body(String.format("User with id %s deleted successfully", id));
  }

  @GetMapping
  public ResponseEntity<List<UserModel>> getAllUsers() {
    return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getUserById(@PathVariable UUID id) {
    Optional<UserModel> userModelOptional = userService.findById(id);
    if (userModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format("User with id %s not found!", id));
    }
    return ResponseEntity.status(HttpStatus.OK).body(userModelOptional.get());
  }
}
