package com.whatsub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.whatsub.domain.entity.TrainTracking;

public interface TrainTrackingRepository extends MongoRepository<TrainTracking, String> {
	Optional<TrainTracking> findByTrainAndCurrentStation(String trainNum, String currentStation);
	List<TrainTracking> findByAlertRequestId(String alertRequestId);
}
