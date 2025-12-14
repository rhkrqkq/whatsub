package com.whatsub.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import com.whatsub.domain.enums.AlertStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "alert_request")
@Getter
@Setter
@NoArgsConstructor
public class AlertRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long userId;
	private Long startStationId;
	private Long endStationId;
	private LocalDateTime departureTime;
	private String trackedTrainNum;
	private String direction; // 상행/하행
	private Integer alertBefore;
	private LocalDateTime lastApi;
	private AlertStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
