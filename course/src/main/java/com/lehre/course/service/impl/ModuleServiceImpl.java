package com.lehre.course.service.impl;

import com.lehre.course.repository.ModuleRepository;
import com.lehre.course.service.ModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl implements ModuleService {
  private final ModuleRepository moduleRepository;

  @Autowired
  public ModuleServiceImpl(ModuleRepository moduleRepository) {
    this.moduleRepository = moduleRepository;
  }
}
