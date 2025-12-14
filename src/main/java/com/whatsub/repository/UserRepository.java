package com.whatsub.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whatsub.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);
}
