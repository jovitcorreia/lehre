package com.lehre.course.service;

import com.lehre.course.model.CourseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CourseService {
  void store(CourseModel courseModel);

  Optional<CourseModel> find(UUID courseId);

  Page<CourseModel> index(Pageable pageable);

  Optional<CourseModel> show(UUID courseId);

  void delete(CourseModel courseModel);
}
