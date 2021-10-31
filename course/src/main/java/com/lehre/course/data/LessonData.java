package com.lehre.course.data;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LessonData {
  @NotBlank private String title;
  @NotBlank private String description;
}
