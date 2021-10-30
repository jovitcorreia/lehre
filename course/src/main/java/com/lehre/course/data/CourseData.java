package com.lehre.course.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lehre.course.constant.CourseLevel;
import com.lehre.course.constant.CourseStatus;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseData {
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
