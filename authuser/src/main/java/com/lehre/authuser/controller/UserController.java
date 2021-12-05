package com.lehre.authuser.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.lehre.authuser.data.UserData;
import com.lehre.authuser.domain.User;
import com.lehre.authuser.repository.SpecTemplate;
import com.lehre.authuser.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
@RestController
@RequestMapping("/users")
public class UserController {
    private static final String USER_NOT_FOUND = "User with id %s not found";
    private static final String USER_UPDATED_SUCCESSFULLY = "User updated successfully with id {}";
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> show(@PathVariable UUID userId) {
        Optional<User> userOptional = userService.find(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format(USER_NOT_FOUND, userId));
        }
        User user = userOptional.get();
        user.add(linkTo(methodOn(UserController.class).show(user.getUserId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping
    public ResponseEntity<Page<User>> index(
        SpecTemplate.UserSpec spec,
        @PageableDefault(sort = "creationDate", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<User> userPage = userService.list(spec, pageable);
        if (!userPage.isEmpty()) {
            for (User user : userPage.toList()) {
                user.add(linkTo(methodOn(UserController.class).show(user.getUserId())).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userPage);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> update(
        @PathVariable UUID userId,
        @RequestBody
        @Validated(UserData.UserView.UpdatePut.class)
        @JsonView(UserData.UserView.UpdatePut.class)
            UserData userData) {
        log.debug("PUT update userData {}", userData.toString());
        Optional<User> userOptional = userService.find(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format(USER_NOT_FOUND, userId));
        }
        User user = userService.update(userData, userOptional.get());
        log.info(USER_UPDATED_SUCCESSFULLY, user.getUserId());
        user.add(linkTo(methodOn(UserController.class).show(user.getUserId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PatchMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(
        @PathVariable UUID userId,
        @RequestBody
        @Validated(UserData.UserView.PasswordPut.class)
        @JsonView(UserData.UserView.PasswordPut.class)
            UserData userData) {
        Optional<User> userOptional = userService.find(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format(USER_NOT_FOUND, userId));
        }
        User user = userOptional.get();
        if (!user.getPassword().equals(userData.getOldPassword())) {
            log.info("Mismatched old password for user with id {}", user.getUserId());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Mismatched old password");
        }
        userService.update(userData, user);
        log.info(USER_UPDATED_SUCCESSFULLY, user.getUserId());
        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
    }

    @PatchMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(
        @PathVariable UUID userId,
        @RequestBody
        @Validated(UserData.UserView.ImagePut.class)
        @JsonView(UserData.UserView.ImagePut.class)
            UserData userData) {
        Optional<User> userOptional = userService.find(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format(USER_NOT_FOUND, userId));
        }
        User user = userService.update(userData, userOptional.get());
        log.info(USER_UPDATED_SUCCESSFULLY, user.getUserId());
        user.add(linkTo(methodOn(UserController.class).show(user.getUserId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> delete(@PathVariable UUID userId) {
        log.debug("DELETE delete user with id {}", userId);
        Optional<User> userOptional = userService.find(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format(USER_NOT_FOUND, userId));
        }
        userService.delete(userOptional.get());
        log.info("User deleted successfully with id {}", userId);
        return ResponseEntity.status(HttpStatus.OK)
            .body(String.format("User with id %s deleted successfully", userId));
    }
}
