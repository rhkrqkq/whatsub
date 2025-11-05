package com.whatsub.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.whatsub.web.dto.RealtimeArrivalDTO;
import com.whatsub.web.dto.SubwayDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubwayApiService {

	private static final String API_URL = "https://api.example.com/subway/realtime?stationCode={stationCode}";

	private final RestTemplate restTemplate = new RestTemplate();

	public List<RealtimeArrivalDTO.RealtimeArrivalResponseDTO> getRealTimeArrivals(String stationCode) {
		try {
			SubwayDTO.SubwayResponseDTO response = restTemplate.getForObject(API_URL, SubwayDTO.SubwayResponseDTO.class, stationCode);

			if (response != null && response.getRealtimeArrivalList() != null) {
				List<RealtimeArrivalDTO.RealtimeArrivalResponseDTO> result = new ArrayList<>();
				for (RealtimeArrivalDTO.RealtimeArrivalResponseDTO arrival : response.getRealtimeArrivalList()) {
					result.add(arrival);
				}
				return result;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<>();
	}

	public SubwayDTO.SubwayResponseDTO getRealTimeArrivals(String stationCode, String direction) {
		SubwayDTO.SubwayResponseDTO response = restTemplate.getForObject(API_URL, SubwayDTO.SubwayResponseDTO.class, stationCode, direction);
		return response;
	}
}
