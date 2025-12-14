package com.whatsub.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.whatsub.domain.entity.Station;

public interface StationRepository extends JpaRepository<Station, Long> {
	List<Station> findByLineId(Long lineId);
	Optional<Station> findByCode(String code);
}
