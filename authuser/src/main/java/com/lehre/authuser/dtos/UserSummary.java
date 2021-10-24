package com.lehre.authuser.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSummary {
  private UUID id;
  private String username;
  private String email;
  private String password;
  private String oldPassword;
  private String fullName;
  private String phoneNumber;
  private String cpf;
  private String imageUrl;
}
