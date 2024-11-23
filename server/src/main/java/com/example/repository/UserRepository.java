package com.example.repository;

import com.example.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query(value = "SELECT * FROM `user` WHERE username = :username", nativeQuery = true)

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}