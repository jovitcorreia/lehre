package com.lehre.course.service;

import com.lehre.course.model.ModuleModel;

import java.util.Optional;
import java.util.UUID;

public interface ModuleService {
  void save(ModuleModel moduleModel);

  Optional<ModuleModel> findModuleIntoCouse(UUID courseId, UUID moduleId);

  void delete(ModuleModel moduleModel);
}
