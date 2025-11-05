package com.whatsub.domain.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.whatsub.domain.enums.NotificationStatus;
import com.whatsub.domain.enums.NotificationType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "notification_log")
@Getter
@Setter
@NoArgsConstructor
public class NotificationLog {
	@Id
	private ObjectId id;

	private ObjectId alertRequestId;

	private NotificationType notiType;
	private String message;
	private NotificationStatus status;
	private String errorMessage;
	private LocalDateTime sentAt;
}
