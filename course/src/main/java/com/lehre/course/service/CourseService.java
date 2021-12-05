package com.lehre.course.service;

import com.lehre.course.data.CourseData;
import com.lehre.course.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {
    @Transactional
    Course create(CourseData courseData);

    Optional<Course> find(UUID courseId);

    Page<Course> list(Pageable pageable);

    @Transactional
    Course update(CourseData courseData, Course course);

    @Transactional
    void delete(Course course);
}
