package com.whatsub.domain.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "train_track")
@Getter
@Setter
@NoArgsConstructor
public class TrainTracking {
	@Id
	private ObjectId id;
	private ObjectId alertRequestId;

	private String trainNum;
	private String currentStation;
	private String arrivalStatus;
	private LocalDateTime lastUpdated;
	private LocalDateTime createdAt;
}
