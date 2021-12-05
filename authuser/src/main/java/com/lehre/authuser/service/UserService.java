package com.lehre.authuser.service;

import com.lehre.authuser.data.UserData;
import com.lehre.authuser.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    @Transactional
    User create(UserData userData);

    Optional<User> find(UUID id);

    Page<User> list(Specification<User> spec, Pageable pageable);

    @Transactional
    User update(UserData userData, User user);

    @Transactional
    void delete(User user);

    boolean checkCpfUnavailability(String cpf);

    boolean checkEmailUnavailability(String email);

    boolean checkUsernameUnavailability(String username);
}
