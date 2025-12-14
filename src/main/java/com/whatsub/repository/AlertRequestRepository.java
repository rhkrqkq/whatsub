package com.whatsub.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whatsub.domain.entity.AlertRequest;
import com.whatsub.domain.enums.AlertStatus;

public interface AlertRequestRepository extends JpaRepository<AlertRequest, Long> {
	List<AlertRequest> findByStatus(AlertStatus alertStatus);
	@Query("SELECT r FROM AlertRequest r WHERE r.status = com.whatsub.domain.enums.AlertStatus.TRACKING AND r.lastApi < ?1")
	List<AlertRequest> findTrackingRequestsNeedingUpdate(LocalDateTime checkTime);

	List<AlertRequest> findByUserId(Long userId);
	List<AlertRequest> findByUserIdAndStatus(Long userId, AlertStatus alertStatus);
}
