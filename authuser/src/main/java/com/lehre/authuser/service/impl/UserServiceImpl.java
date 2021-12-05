package com.lehre.authuser.service.impl;

import com.lehre.authuser.data.UserData;
import com.lehre.authuser.domain.User;
import com.lehre.authuser.domain.UserStatus;
import com.lehre.authuser.domain.UserType;
import com.lehre.authuser.repository.UserRepository;
import com.lehre.authuser.service.UserService;
import com.lehre.authuser.util.Rejector;
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
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User create(UserData userData) {
        var user = new User();
        BeanUtils.copyProperties(userData, user);
        user.setStatus(UserStatus.ACTIVE);
        user.setType(UserType.STUDENT);
        user.setCreationDate(LocalDateTime.now(ZoneId.of("UTC")));
        user.setLastUpdateDate(user.getCreationDate());
        return userRepository.save(user);
    }

    @Override
    public Optional<User> find(UUID id) {
        return userRepository.findById(id);
    }

    @Override
    public Page<User> list(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public User update(UserData userData, User user) {
        BeanUtils.copyProperties(userData, user, Rejector.rejectNullValues(userData));
        user.setLastUpdateDate(LocalDateTime.now(ZoneId.of("UTC")));
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public boolean checkCpfUnavailability(String cpf) {
        return userRepository.existsByCpf(cpf);
    }

    @Override
    public boolean checkEmailUnavailability(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean checkUsernameUnavailability(String username) {
        return userRepository.existsByUsername(username);
    }
}
