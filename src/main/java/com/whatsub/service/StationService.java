package com.whatsub.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.whatsub.domain.entity.Station;
import com.whatsub.repository.StationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class StationService {
	private final StationRepository stationRepository;

	// 특정 노선에 속한 역 목록 조회
	public List<Station> getStationsByLine(Long lineId) {
		return stationRepository.findByLineId(lineId);
	}

	// 역 코드로 특정 역 조회
	public Optional<Station> getStationByCode(String code) {
		return stationRepository.findByCode(code);
	}

	public Optional<Station> getStationById(Long id) {
		return stationRepository.findById(id);
	}
}
