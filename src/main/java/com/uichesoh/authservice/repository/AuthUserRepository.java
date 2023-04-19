package com.uichesoh.authservice.repository;

import com.uichesoh.authservice.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthUserRepository extends JpaRepository<AuthUser,Integer> {
    Optional<AuthUser> findByUsername(String username);
}
