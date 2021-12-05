package com.lehre.course.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lehre.course.domain.CourseLevel;
import com.lehre.course.domain.CourseStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseDto {
  @NotBlank
  private String name;
  @NotBlank
  private String description;
  private String imageUrl;
  @NotNull
  private CourseStatus status;
  @NotNull
  private CourseLevel level;
  @NotNull
  private UUID instructor;
}
