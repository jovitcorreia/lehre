package com.lehre.course.mapper;

import com.lehre.course.constant.CourseLevel;
import com.lehre.course.constant.CourseStatus;
import com.lehre.course.data.CourseData;
import com.lehre.course.model.CourseModel;
import com.lehre.course.util.Mapper;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class CourseMapper implements Mapper<CourseData, CourseModel> {
  @Override
  public CourseModel from(CourseData courseData) {
    return CourseModel.builder()
        .name(courseData.getName())
        .description(courseData.getDescription())
        .imageUrl(courseData.getImageUrl())
        .status(courseData.getStatus())
        .level(courseData.getLevel())
        .instructor(courseData.getInstructor())
        .creationDate(LocalDateTime.now(ZoneId.of("UTC")))
        .lastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")))
        .build();
  }
}
