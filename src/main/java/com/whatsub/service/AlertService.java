package com.whatsub.service;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.whatsub.domain.entity.AlertRequest;
import com.whatsub.domain.entity.Station;
import com.whatsub.domain.entity.User;
import com.whatsub.domain.enums.AlertStatus;
import com.whatsub.repository.AlertRequestRepository;
import com.whatsub.web.dto.AlertCreateDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlertService {
	private final AlertRequestRepository alertRequestRepository;
	private final UserService userService;
	private final StationService stationService;
	private final TrainTrackingService trainTrackingService;

	public ObjectId createAlert(AlertCreateDTO.AlertCreateRequestDTO requestDTO) {
		User user = userService.findorCreateUser(requestDTO.getEmail(), requestDTO.getFcmToken());
		Station startStation = stationService.getStationById(new ObjectId(requestDTO.getStartStationId()))
			.orElseThrow(()->new IllegalArgumentException("출발역을 찾을 수 없습니다."));
		Station endStation = stationService.getStationById(new ObjectId(requestDTO.getEndStationId()))
			.orElseThrow(()->new IllegalArgumentException("도착역을 찾을 수 없습니다."));

		AlertRequest alertRequest = new AlertRequest();
		alertRequest.setUserId(user.getId());
		alertRequest.setStartStationId(startStation.getId());
		alertRequest.setEndStationId(endStation.getId());
		alertRequest.setDepartureTime(requestDTO.getDepartureTime());
		alertRequest.setDirection(requestDTO.getDirection());
		alertRequest.setAlertBefore(requestDTO.getAlertBefore());
		alertRequest.setStatus(AlertStatus.PENDING);
		alertRequest.setCreatedAt(LocalDateTime.now());

		AlertRequest saveAlert = alertRequestRepository.save(alertRequest);

		CompletableFuture.runAsync(()-> {
			try {
				trainTrackingService.startTracking(saveAlert);
			} catch (Exception e) {
				log.error("열차 추적 실패: {}", e.getMessage());
			}
		});

		return saveAlert.getId();
	}
}
