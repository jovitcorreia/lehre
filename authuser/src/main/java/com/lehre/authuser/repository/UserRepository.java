package com.lehre.authuser.repository;

import com.lehre.authuser.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User> {
    boolean existsByCpf(String cpf);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);
}
