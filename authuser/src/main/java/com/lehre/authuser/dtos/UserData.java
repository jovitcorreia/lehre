package com.lehre.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserData {
  private UUID id;
  @JsonView(UserView.RegistrationPost.class)
  private String username;
  @JsonView(UserView.RegistrationPost.class)
  private String email;
  @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
  private String password;
  @JsonView(UserView.PasswordPut.class)
  private String oldPassword;
  @JsonView({UserView.RegistrationPost.class, UserView.GenericPut.class})
  private String fullName;
  @JsonView({UserView.RegistrationPost.class, UserView.GenericPut.class})
  private String phoneNumber;
  @JsonView({UserView.RegistrationPost.class, UserView.GenericPut.class})
  private String cpf;
  @JsonView(UserView.ImagePut.class)
  private String imageUrl;

  public interface UserView {
    interface RegistrationPost {}

    interface GenericPut {}

    interface PasswordPut {}

    interface ImagePut {}
  }
}
