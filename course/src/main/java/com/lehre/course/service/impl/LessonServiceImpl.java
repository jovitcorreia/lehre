package com.lehre.course.service.impl;

import com.lehre.course.data.LessonData;
import com.lehre.course.domain.Lesson;
import com.lehre.course.domain.Module;
import com.lehre.course.repository.LessonRepository;
import com.lehre.course.service.LessonService;
import com.lehre.course.util.Rejector;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
    public Lesson create(Module module, LessonData lessonData) {
        var lesson = new Lesson();
        lesson.setModuleId(module);
        BeanUtils.copyProperties(lessonData, lesson);
        lesson.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        lesson.setLastUpdateDate(lesson.getCreationDate());
        return lessonRepository.save(lesson);
    }

    @Override
    public Optional<Lesson> findLessonIntoModule(UUID moduleId, UUID lessonId) {
        return lessonRepository.findLessonIntoModule(moduleId, lessonId);
    }

    @Override
    public Page<Lesson> list(Specification<Lesson> spec, Pageable pageable) {
        return lessonRepository.findAll(spec, pageable);
    }

    @Override
    public Lesson update(LessonData lessonData, Lesson lesson) {
        BeanUtils.copyProperties(lessonData, lesson, Rejector.rejectNullValues(lessonData));
        return lessonRepository.save(lesson);
    }

    @Override
    public void delete(Lesson lesson) {
        lessonRepository.delete(lesson);
    }
}
