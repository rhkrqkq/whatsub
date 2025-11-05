package com.whatsub.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.whatsub.domain.entity.NotificationLog;
import com.whatsub.domain.enums.NotificationStatus;

public interface NotificationLogRepository extends MongoRepository<NotificationLog, String> {
	List<NotificationLog> findByAlertRequestId(ObjectId alertRequestId);

	List<NotificationLog> findByAlertRequestIdAndStatus(ObjectId alertRequestId);

	List<NotificationLog> findByStatus(NotificationStatus status);

	@Query("{ 'alterRequestId: ?0'}")
	List<NotificationLog> findLastestByAlertRequestId(ObjectId alertRequestId);

	@Query("{ 'status':  'FAIL', 'sentAt':  { $gte:  ?0, $lte: ?1} }")
	List<NotificationLog> findFailedNotificationsBetween(LocalDateTime startTime, LocalDateTime endTime);
}
