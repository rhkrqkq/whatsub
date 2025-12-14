package com.whatsub.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.whatsub.domain.entity.Station;
import com.whatsub.web.dto.RealtimeArrivalDTO;
import com.whatsub.web.dto.SubwayDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubwayApiService {

	@Value("${subway.api.key}")
	private String apiKey;

	@Value("${subway.api.url.base}")
	private String apiUrlBase;

	private final RestTemplate restTemplate = new RestTemplate();
	private final StationService stationService;


	public List<RealtimeArrivalDTO.RealtimeArrivalResponseDTO> getRealTimeArrivals(String stationCode) {
		Optional<Station> stationOpt = stationService.getStationByCode(stationCode); //
		if (stationOpt.isEmpty()) {
			log.warn("해당 StationCode로 역을 찾을 수 없습니다: {}", stationCode);
			return new ArrayList<>();
		}
		String stationName = stationOpt.get().getName();

		String apiUrl = UriComponentsBuilder.fromUriString(apiUrlBase)
			.buildAndExpand(apiKey, stationName)
			.encode()
			.toUriString();

		try {
			SubwayDTO.SubwayResponseDTO response = restTemplate.getForObject(apiUrl, SubwayDTO.SubwayResponseDTO.class);

			if (response != null && response.getRealtimeArrivalList() != null) {
				return response.getRealtimeArrivalList();
			}
		} catch (Exception e) {
			log.error("지하철 실시간 도착 정보 조회 실패 - Station: {}, Error: {}", stationName, e.getMessage());
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public SubwayDTO.SubwayResponseDTO getRealTimeArrivals(String stationCode, String direction) {
		SubwayDTO.SubwayResponseDTO response = restTemplate.getForObject(apiUrlBase, SubwayDTO.SubwayResponseDTO.class, stationCode, direction);
		return response;
	}
}
