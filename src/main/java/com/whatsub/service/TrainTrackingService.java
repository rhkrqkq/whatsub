package com.whatsub.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.javapoet.ClassName;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.whatsub.domain.entity.AlertRequest;
import com.whatsub.domain.entity.Station;
import com.whatsub.domain.entity.TrainTracking;
import com.whatsub.domain.enums.AlertStatus;
import com.whatsub.repository.AlertRequestRepository;
import com.whatsub.repository.TrainTrackingRepository;
import com.whatsub.web.dto.RealtimeArrivalDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrainTrackingService {

	private final AlertRequestRepository alertRepository;
	private final TrainTrackingRepository trackingRepository;
	private final StationService stationService;
	private final SubwayApiService subwayApiService;
	private final NotificationService notificationService;


	public void startTracking(AlertRequest request) {
		try {
			// 출발 시간 기준으로 해당 역에서 탈 수 있는 열차 찾기
			String trainNum = identifyUserTrain(request);

			if (trainNum != null) {
				request.setTrackedTrainNum(trainNum);
				request.setStatus(AlertStatus.TRACKING);
				alertRepository.save(request);

				log.info("열차 추적 시작 - Request ID: {}, Train: {}",
					request.getId(), trainNum);
			} else {
				log.warn("추적할 열차를 찾을 수 없습니다 - Request ID: {}", request.getId());
				request.setStatus(AlertStatus.EXPIRED);
				alertRepository.save(request);
			}
		} catch (Exception e) {
			log.error("열차 추적 시작 실패: {}", e.getMessage());
			request.setStatus(AlertStatus.EXPIRED);
			alertRepository.save(request);
		}
	}

	private String identifyUserTrain(AlertRequest request) {
		LocalDateTime searchStart = request.getDepartureTime().minusMinutes(5);
		LocalDateTime searchEnd = request.getDepartureTime().plusMinutes(5);

		Station startStation = stationService.getStationById(request.getStartStationId())
			.orElseThrow(() -> new IllegalArgumentException("출발역을 찾을 수 없습니다"));

		List<RealtimeArrivalDTO.RealtimeArrivalResponseDTO> arrivals =
				subwayApiService.getRealTimeArrivals(startStation.getCode());

		return arrivals.stream()
			.filter(arrival -> arrival.getDirection().equals(request.getDirection()))
			.filter(arrival -> isWithinTimeRange(arrival, searchStart, searchEnd))
			.map(RealtimeArrivalDTO.RealtimeArrivalResponseDTO::getTrainNum)
			.findFirst()
			.orElse(null);
	}

	private boolean isWithinTimeRange(RealtimeArrivalDTO.RealtimeArrivalResponseDTO arrival,
		LocalDateTime start, LocalDateTime end) {
		return "1".equals(arrival.getArrivalCode()) || "5".equals(arrival.getArrivalCode());
	}

	@Scheduled(fixedRate = 30000)
	public void trackAllTrains() {
		List<AlertRequest> trackingRequests =
			alertRepository.findByStatus(AlertStatus.TRACKING);

		log.info("추적 중인 요청 수: {}", trackingRequests.size());

		for (AlertRequest request : trackingRequests) {
			try {
				trackSingleTrain(request);
			} catch (Exception e) {
				log.error("열차 추적 실패 - Request ID: {}, Error: {}",
					request.getId(), e.getMessage());
			}
		}
	}

	private void trackSingleTrain(AlertRequest request) {
		String trainNum = request.getTrackedTrainNum();

		Station endStation = stationService.getStationById(request.getEndStationId())
			.orElseThrow(() -> new IllegalArgumentException("도착역을 찾을 수 없습니다"));
		String endStationCode = endStation.getCode();

		List<RealtimeArrivalDTO.RealtimeArrivalResponseDTO> arrivals = subwayApiService.getRealTimeArrivals(endStation.getCode());

		Optional<RealtimeArrivalDTO.RealtimeArrivalResponseDTO> targetTrain = arrivals.stream()
			.filter(arrival -> trainNum.equals(arrival.getTrainNum()))
			.findFirst();

		if (targetTrain.isPresent()) {
			RealtimeArrivalDTO.RealtimeArrivalResponseDTO train = targetTrain.get();
			updateTrackingInfo(request, train);

			if (shouldSendAlert(train, request)) {
				notificationService.sendAlert(request, train);
				request.setStatus(AlertStatus.SENT);
				alertRepository.save(request);
			}
		} else {
			log.warn("추적 중인 열차를 찾을 수 없음 - Request ID: {}, Train: {}",
				request.getId(), trainNum);
		}

		request.setLastApi(LocalDateTime.now());
		alertRepository.save(request);
	}

	private void updateTrackingInfo(AlertRequest request, RealtimeArrivalDTO.RealtimeArrivalResponseDTO train) {
		TrainTracking tracking = new TrainTracking();
		tracking.setAlertRequestId(request.getId());
		tracking.setTrainNum(train.getTrainNum());
		tracking.setCurrentStation(train.getStationName());
		tracking.setArrivalStatus(train.getMessage());
		tracking.setLastUpdated(LocalDateTime.now());
		tracking.setCreatedAt(LocalDateTime.now());

		trackingRepository.save(tracking);
	}

	private boolean shouldSendAlert(RealtimeArrivalDTO.RealtimeArrivalResponseDTO train, AlertRequest request) {
		String arrivalMessage = train.getMessage();

		Pattern pattern = Pattern.compile("\\[(\\d+)\\]번째 전역");
		Matcher matcher = pattern.matcher(arrivalMessage);

		if (matcher.find()) {
			int stationsLeft = Integer.parseInt(matcher.group(1));
			return stationsLeft <= request.getAlertBefore();
		}

		return arrivalMessage.contains("전역 도착") || arrivalMessage.contains("도착");
	}
}
