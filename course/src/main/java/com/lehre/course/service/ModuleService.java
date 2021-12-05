package com.lehre.course.service;

import com.lehre.course.data.ModuleData;
import com.lehre.course.domain.Course;
import com.lehre.course.domain.Module;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface ModuleService {
    Module create(Course course, ModuleData moduleData);

    Optional<Module> findModuleIntoCouse(UUID courseId, UUID moduleId);

    Page<Module> list(Specification<Module> spec, Pageable pageable);

    @Transactional
    Module update(ModuleData moduleData, Module module);

    @Transactional
    void delete(Module module);
}
