package com.whatsub.domain.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.whatsub.domain.enums.AlertStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "alert_request")
@Getter
@Setter
@NoArgsConstructor
public class AlertRequest {
	@Id
	private ObjectId id;

	private ObjectId userId;
	private ObjectId startStationId;
	private ObjectId endStationId;
	private LocalDateTime departureTime;
	private String trackedTrainNum;
	private String direction; // 상행/하행
	private Integer alertBefore;
	private LocalDateTime lastApi;
	private AlertStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
