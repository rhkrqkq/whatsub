package com.whatsub.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whatsub.domain.entity.User;
import com.whatsub.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
	private final UserRepository userRepository;

	public User findorCreateUser(String email, String fcmToken) {
		return userRepository.findByEmail(email)
			.map(user -> {
				user.setFcmToken(fcmToken);
				return userRepository.save(user);
			})
			.orElseGet(()-> {
				User user = new User();
				user.setEmail(email);
				user.setFcmToken(fcmToken);
				user.setCreatedAt(LocalDateTime.now());
				return userRepository.save(user);
			});
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
}
