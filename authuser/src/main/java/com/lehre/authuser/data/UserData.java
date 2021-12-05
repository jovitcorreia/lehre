package com.lehre.authuser.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.lehre.authuser.data.validation.UsernameConstraint;
import lombok.Data;
import lombok.ToString;

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
    @ToString.Exclude
    private String password;

    @JsonView(UserView.PasswordPut.class)
    @NotBlank(groups = UserView.PasswordPut.class)
    @ToString.Exclude
    private String oldPassword;

    @JsonView({UserView.RegistrationPost.class, UserView.UpdatePut.class})
    private String fullName;

    @JsonView({UserView.RegistrationPost.class, UserView.UpdatePut.class})
    @Size(groups = {UserView.RegistrationPost.class, UserView.UpdatePut.class}, max = 32)
    private String phoneNumber;

    @JsonView({UserView.RegistrationPost.class, UserView.UpdatePut.class})
    @Size(groups = {UserView.RegistrationPost.class, UserView.UpdatePut.class}, max = 32)
    private String cpf;

    @NotBlank(groups = UserView.ImagePut.class)
    @JsonView(UserView.ImagePut.class)
    private String imageUrl;

    public interface UserView {
        interface RegistrationPost {
        }

        interface UpdatePut {
        }

        interface PasswordPut {
        }

        interface ImagePut {
        }
    }
}
