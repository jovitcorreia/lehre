package com.lehre.course.service.impl;

import com.lehre.course.data.ModuleData;
import com.lehre.course.domain.Course;
import com.lehre.course.domain.Module;
import com.lehre.course.repository.ModuleRepository;
import com.lehre.course.service.ModuleService;
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
public class ModuleServiceImpl implements ModuleService {
    private final ModuleRepository moduleRepository;

    @Autowired
    public ModuleServiceImpl(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @Override
    public Module create(Course course, ModuleData moduleData) {
        var module = new Module();
        BeanUtils.copyProperties(moduleData, module);
        module.setCourseId(course);
        module.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        module.setLastUpdateDate(module.getCreationDate());
        return moduleRepository.save(module);
    }

    @Override
    public Optional<Module> findModuleIntoCouse(UUID courseId, UUID moduleId) {
        return moduleRepository.findModuleIntoCourse(courseId, moduleId);
    }

    public Module update(ModuleData moduleData, Module module) {
        BeanUtils.copyProperties(moduleData, module, Rejector.rejectNullValues(moduleData));
        module.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return moduleRepository.save(module);
    }

    public Page<Module> list(Specification<Module> spec, Pageable pageable) {
        return moduleRepository.findAll(spec, pageable);
    }

    @Override
    public void delete(Module module) {
        moduleRepository.delete(module);
    }
}
