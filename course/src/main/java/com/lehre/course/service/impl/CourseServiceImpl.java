package com.lehre.course.service.impl;

import com.lehre.course.model.CourseModel;
import com.lehre.course.repository.CourseRepository;
import com.lehre.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class CourseServiceImpl implements CourseService {
  private final CourseRepository courseRepository;

  @Autowired
  public CourseServiceImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public void save(CourseModel courseModel) {
    this.courseRepository.save(courseModel);
  }

  @Override
  public Page<CourseModel> findAll(Pageable pageable) {
    return this.courseRepository.findAll(pageable);
  }

  @Override
  public Optional<CourseModel> findById(UUID courseId) {
    return this.courseRepository.findById(courseId);
  }

  @Override
  public void delete(CourseModel courseModel) {
    this.courseRepository.delete(courseModel);
  }
}
