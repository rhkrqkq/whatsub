package com.whatsub.domain.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "train_track")
@Getter
@Setter
@NoArgsConstructor
public class TrainTracking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long alertRequestId;

	private String trainNum;
	private String currentStation;
	private String arrivalStatus;
	private LocalDateTime lastUpdated;
	private LocalDateTime createdAt;
}
