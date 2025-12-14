package com.whatsub.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whatsub.domain.entity.NotificationLog;
import com.whatsub.domain.enums.NotificationStatus;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, Long> {
	List<NotificationLog> findByAlertRequestIdAndStatus(Long alertRequestId, NotificationStatus status);

	List<NotificationLog> findByAlertRequestId(Long alertRequestId);

	List<NotificationLog> findByStatus(NotificationStatus status);

	NotificationLog findTop1ByAlertRequestIdOrderByIdDesc(Long alertRequestId);

	@Query("SELECT nl FROM NotificationLog nl WHERE nl.status = com.whatsub.domain.enums.NotificationStatus.FAIL AND nl.sentAt BETWEEN ?1 AND ?2")
	List<NotificationLog> findFailedNotificationsBetween(LocalDateTime startTime, LocalDateTime endTime);
}
