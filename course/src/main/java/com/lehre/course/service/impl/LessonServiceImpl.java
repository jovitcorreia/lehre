package com.lehre.course.service.impl;

import com.lehre.course.model.LessonModel;
import com.lehre.course.repository.LessonRepository;
import com.lehre.course.service.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class LessonServiceImpl implements LessonService {
  private final LessonRepository lessonRepository;

  @Autowired
  public LessonServiceImpl(LessonRepository lessonRepository) {
    this.lessonRepository = lessonRepository;
  }

  @Override
  public void save(LessonModel lessonModel) {
    lessonRepository.save(lessonModel);
  }

  @Override
  public Page<LessonModel> findAll(Specification<LessonModel> spec, Pageable pageable) {
    return lessonRepository.findAll(spec, pageable);
  }

  @Override
  public Optional<LessonModel> findLessonIntoModule(UUID moduleId, UUID lessonId) {
    return lessonRepository.findLessonIntoModule(moduleId, lessonId);
  }

  @Override
  public void delete(LessonModel lessonModel) {
    lessonRepository.delete(lessonModel);
  }
}
