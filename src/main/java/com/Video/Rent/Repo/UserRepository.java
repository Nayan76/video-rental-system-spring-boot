package com.Video.Rent.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Video.Rent.Entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    
}
