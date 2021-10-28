package com.lehre.course.service.impl;

import com.lehre.course.model.CourseModel;
import com.lehre.course.model.ModuleModel;
import com.lehre.course.repository.CourseRepository;
import com.lehre.course.repository.ModuleRepository;
import com.lehre.course.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
  private final CourseRepository courseRepository;

  @Autowired
  public CourseServiceImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }
}
