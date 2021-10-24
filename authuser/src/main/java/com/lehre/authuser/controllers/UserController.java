package com.lehre.authuser.controllers;

import com.lehre.authuser.models.UserModel;
import com.lehre.authuser.services.UserService;
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

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteUserById(@PathVariable UUID id) {
    Optional<UserModel> userModelOptional = userService.findById(id);
    if (userModelOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    userService.delete(userModelOptional.get());
    return ResponseEntity.ok(String.format("User with id %s deleted successfully", id));
  }

  @GetMapping
  public ResponseEntity<List<UserModel>> getAllUsers() {
    return ResponseEntity.ok(userService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserModel> getUserById(@PathVariable UUID id) {
    Optional<UserModel> userModelOptional = userService.findById(id);
    if (userModelOptional.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(userModelOptional.get());
  }
}
