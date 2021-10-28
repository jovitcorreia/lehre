package com.lehre.course.service.impl;

import com.lehre.course.repository.LessonRepository;
import com.lehre.course.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {
  private final LessonRepository lessonRepository;

  @Autowired
  public LessonServiceImpl(LessonRepository lessonRepository) {
    this.lessonRepository = lessonRepository;
  }
}
