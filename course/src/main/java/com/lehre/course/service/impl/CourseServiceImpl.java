package com.lehre.course.service.impl;

import com.lehre.course.data.CourseData;
import com.lehre.course.domain.Course;
import com.lehre.course.repository.CourseRepository;
import com.lehre.course.service.CourseService;
import com.lehre.course.util.Rejector;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
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
    public Course create(CourseData courseData) {
        var course = new Course();
        BeanUtils.copyProperties(courseData, course);
        course.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        course.setLastUpdateDate(course.getCreationDate());
        return courseRepository.save(course);
    }

    @Override
    public Optional<Course> find(UUID courseId) {
        return courseRepository.findById(courseId);
    }

    @Override
    public Page<Course> list(Pageable pageable) {
        return courseRepository.findAll(pageable);
    }

    @Override
    public Course update(CourseData courseData, Course course) {
        BeanUtils.copyProperties(courseData, course, Rejector.rejectNullValues(courseData));
        course.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return courseRepository.save(course);
    }

    @Override
    public void delete(Course course) {
        courseRepository.delete(course);
    }
}
