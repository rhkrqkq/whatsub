package com.whatsub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whatsub.domain.entity.TrainTracking;

public interface TrainTrackingRepository extends JpaRepository<TrainTracking, Long> {
	Optional<TrainTracking> findByTrainNumAndCurrentStation(String trainNum, String currentStation);

	List<TrainTracking> findByAlertRequestId(Long alertRequestId);
}
