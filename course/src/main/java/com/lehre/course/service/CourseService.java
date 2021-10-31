package com.lehre.course.service;

import com.lehre.course.model.CourseModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface CourseService {
  void save(CourseModel courseModel);

  Page<CourseModel> findAll(Pageable pageable);

  Optional<CourseModel> findById(UUID courseId);

  void delete(CourseModel courseModel);
}
