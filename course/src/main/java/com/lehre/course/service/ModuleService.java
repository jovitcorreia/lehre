package com.lehre.course.service;

import com.lehre.course.model.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface ModuleService {
  void save(ModuleModel moduleModel);

  Page<ModuleModel> findAll(Specification<ModuleModel> spec, Pageable pageable);

  Optional<ModuleModel> findModuleIntoCouse(UUID courseId, UUID moduleId);

  void delete(ModuleModel moduleModel);
}
