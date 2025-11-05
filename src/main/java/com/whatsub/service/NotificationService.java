package com.whatsub.service;

import java.time.LocalDateTime;

import javax.management.Notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.whatsub.domain.entity.AlertRequest;
import com.whatsub.domain.entity.NotificationLog;
import com.whatsub.domain.entity.User;
import com.whatsub.domain.enums.NotificationStatus;
import com.whatsub.domain.enums.NotificationType;
import com.whatsub.repository.NotificationLogRepository;
import com.whatsub.repository.UserRepository;
import com.whatsub.web.dto.RealtimeArrivalDTO;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@NoArgsConstructor
@Slf4j
public class NotificationService {
	@Autowired
	private NotificationLogRepository notificationLogRepository;
	@Autowired
	private UserService userService;

	public void sendAlert(AlertRequest request, RealtimeArrivalDTO.RealtimeArrivalResponseDTO train) {
		String message = buildAlertMessage(request, train);
		try {
			User user = userService.findByEmail(request.getUserId().toHexString())
				.orElseThrow(()->new IllegalArgumentException("사용자를 찾을 수 없습니다."));
			sendPushNotification(user.getFcmToken(), message);
			logNotification(request, NotificationType.PUSH, message, NotificationStatus.SUCCESS, null);
			log.info("알림 발송 성공 - User: {}, Message: {}", user.getEmail(), message);
		} catch (Exception e) {
			logNotification(request, NotificationType.PUSH, message, NotificationStatus.FAIL, e.getMessage());
			log.error("알림 발송 실패: {}", e.getMessage());
		}
	}

	public String buildAlertMessage(AlertRequest request, RealtimeArrivalDTO.RealtimeArrivalResponseDTO train) {
		return String.format("%s역에 곧 도착합니다. 하차 준비하세요. (열차번호: %s)", train.getStationName(), train.getTrainNum());
	}

	public void sendPushNotification(String fcmToken, String message) {

	}

	private void logNotification(AlertRequest request, NotificationType type, String message, NotificationStatus status, String error) {
		NotificationLog log = new NotificationLog();
		log.setAlertRequestId(request.getId());
		log.setNotiType(type);
		log.setMessage(message);
		log.setStatus(status);
		log.setErrorMessage(error);
		log.setSentAt(LocalDateTime.now());
		notificationLogRepository.save(log);
	}
}
