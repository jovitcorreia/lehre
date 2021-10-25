package com.lehre.authuser.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import com.lehre.authuser.validation.UsernameConstraint;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserData {
  private UUID id;

  @JsonView(UserView.RegistrationPost.class)
  @NotBlank(groups = UserView.RegistrationPost.class)
  @Size(groups = UserView.RegistrationPost.class, min = 3, max = 32)
  @UsernameConstraint(groups = UserData.UserView.RegistrationPost.class)
  private String username;

  @Email(groups = UserView.RegistrationPost.class)
  @JsonView(UserView.RegistrationPost.class)
  @NotBlank(groups = UserView.RegistrationPost.class)
  private String email;

  @JsonView({UserView.RegistrationPost.class, UserView.PasswordPut.class})
  @NotBlank(groups = {UserView.RegistrationPost.class, UserView.PasswordPut.class})
  private String password;

  @JsonView(UserView.PasswordPut.class)
  @NotBlank(groups = UserView.PasswordPut.class)
  private String oldPassword;

  @JsonView({UserView.RegistrationPost.class, UserView.GenericPut.class})
  private String fullName;

  @JsonView({UserView.RegistrationPost.class, UserView.GenericPut.class})
  @Size(groups = {UserView.RegistrationPost.class, UserView.GenericPut.class}, max = 32)
  private String phoneNumber;

  @JsonView({UserView.RegistrationPost.class, UserView.GenericPut.class})
  @Size(groups = {UserView.RegistrationPost.class, UserView.GenericPut.class}, max = 32)
  private String cpf;

  @NotBlank(groups = UserView.ImagePut.class)
  @JsonView(UserView.ImagePut.class)
  private String imageUrl;

  public interface UserView {
    interface RegistrationPost {}

    interface GenericPut {}

    interface PasswordPut {}

    interface ImagePut {}
  }
}
