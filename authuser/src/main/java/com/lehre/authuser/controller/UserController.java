package com.lehre.authuser.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.lehre.authuser.data.UserData;
import com.lehre.authuser.model.UserModel;
import com.lehre.authuser.service.UserService;
import com.lehre.authuser.spec.SpecTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {
  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> show(@PathVariable UUID id) {
    Optional<UserModel> userModelOptional = userService.show(id);
    if (userModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format("User with id %s not found!", id));
    }
    UserModel userModel = userModelOptional.get();
    userModel.add(linkTo(methodOn(UserController.class).show(userModel.getId())).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(userModel);
  }

  @GetMapping
  public ResponseEntity<Page<UserModel>> index(
      SpecTemplate.UserSpec spec,
      @PageableDefault(sort = "creationDate", direction = Sort.Direction.ASC) Pageable pageable) {
    Page<UserModel> userModelPage = userService.index(spec, pageable);
    if (!userModelPage.isEmpty()) {
      for (UserModel user : userModelPage.toList()) {
        user.add(linkTo(methodOn(UserController.class).show(user.getId())).withSelfRel());
      }
    }
    return ResponseEntity.status(HttpStatus.OK).body(userModelPage);
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> update(
      @PathVariable UUID id,
      @RequestBody
          @Validated(UserData.UserView.GenericPut.class)
          @JsonView(UserData.UserView.GenericPut.class)
          UserData userData) {
    Optional<UserModel> userModelOptional = userService.show(id);
    if (userModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format("User with id %s not found!", id));
    }
    UserModel userModel = userModelOptional.get();
    userModel.setCpf(userData.getCpf());
    userModel.setFullName(userData.getFullName());
    userModel.setPhoneNumber(userData.getPhoneNumber());
    userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
    userService.store(userModel);
    userModel.add(linkTo(methodOn(UserController.class).show(userModel.getId())).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(userModel);
  }

  @PutMapping("/{id}/password")
  public ResponseEntity<?> updatePassword(
      @PathVariable UUID id,
      @RequestBody
          @Validated(UserData.UserView.PasswordPut.class)
          @JsonView(UserData.UserView.PasswordPut.class)
          UserData userData) {
    Optional<UserModel> userModelOptional = userService.show(id);
    if (userModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format("User with id %s not found!", id));
    }
    UserModel userModel = userModelOptional.get();
    if (!userModel.getPassword().equals(userData.getOldPassword())) {
      return ResponseEntity.status(HttpStatus.CONFLICT).body("Mismatched old password!");
    }
    userModel.setPassword(userData.getPassword());
    userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
    userService.store(userModel);
    return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully!");
  }

  @PutMapping("/{id}/image")
  public ResponseEntity<?> updateImage(
      @PathVariable UUID id,
      @RequestBody
          @Validated(UserData.UserView.ImagePut.class)
          @JsonView(UserData.UserView.ImagePut.class)
          UserData userData) {
    Optional<UserModel> userModelOptional = userService.show(id);
    if (userModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format("User with id %s not found!", id));
    }
    UserModel userModel = userModelOptional.get();
    userModel.setImageUrl(userData.getImageUrl());
    userModel.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
    userService.store(userModel);
    userModel.add(linkTo(methodOn(UserController.class).show(userModel.getId())).withSelfRel());
    return ResponseEntity.status(HttpStatus.OK).body(userModel);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> delete(@PathVariable UUID id) {
    Optional<UserModel> userModelOptional = userService.show(id);
    if (userModelOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND)
          .body(String.format("User with id %s not found!", id));
    }
    userService.delete(userModelOptional.get());
    return ResponseEntity.status(HttpStatus.OK)
        .body(String.format("User with id %s deleted successfully", id));
  }
}