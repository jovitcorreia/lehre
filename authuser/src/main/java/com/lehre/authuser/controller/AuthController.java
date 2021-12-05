package com.lehre.authuser.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.lehre.authuser.data.UserData;
import com.lehre.authuser.domain.User;
import com.lehre.authuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    public ResponseEntity<Object> store(
        @RequestBody
        @Validated(UserData.UserView.RegistrationPost.class)
        @JsonView(UserData.UserView.RegistrationPost.class)
            UserData userData) {
        if (userService.checkUsernameUnavailability(userData.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username is already taken!");
        } else if (userService.checkEmailUnavailability(userData.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(("Email is already taken!"));
        } else if (userService.checkCpfUnavailability(userData.getCpf())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("CPF is already taken!");
        }
        User user = userService.create(userData);
        user.add(linkTo(methodOn(UserController.class).show(user.getUserId())).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
