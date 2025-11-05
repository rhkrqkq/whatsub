package com.whatsub.domain.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "user")
@Getter
@Setter
@NoArgsConstructor
public class User {
	@Id
	private ObjectId id;

	private String email;
	private String fcmToken;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
