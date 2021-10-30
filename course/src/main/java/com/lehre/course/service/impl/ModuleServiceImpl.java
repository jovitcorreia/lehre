package com.lehre.course.service.impl;

import com.lehre.course.model.ModuleModel;
import com.lehre.course.repository.ModuleRepository;
import com.lehre.course.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
  public void save(ModuleModel moduleModel) {
    moduleRepository.save(moduleModel);
  }

  @Override
  public Optional<ModuleModel> findModuleIntoCouse(UUID courseId, UUID moduleId) {
    return moduleRepository.findModuleIntoCourse(courseId, moduleId);
  }

  @Override
  public void delete(ModuleModel moduleModel) {
    moduleRepository.delete(moduleModel);
  }
}
