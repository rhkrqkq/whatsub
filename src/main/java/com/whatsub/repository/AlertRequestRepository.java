package com.whatsub.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.whatsub.domain.entity.AlertRequest;
import com.whatsub.domain.enums.AlertStatus;

public interface AlertRequestRepository extends MongoRepository<AlertRequest, String> {
	List<AlertRequest> findByStatus(AlertStatus alertStatus);
	@Query("{ 'status': 'TRACKING', 'lastApiCheck':  {$lt:  ?0} }")
	List<AlertRequest> findTrackingRequestsNeedingUpdate(LocalDateTime checkTime);
}
