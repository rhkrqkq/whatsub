package com.whatsub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.whatsub.domain.entity.Station;

public interface StationRepository extends MongoRepository<Station, String> {
	List<Station> findByLineId(String lineId);
	Optional<Station> findByStationCode(String stationCode);
}
