package com.whatsub.web.dto;

import java.util.List;

import com.whatsub.api.ErrorMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class SubwayDTO {
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SubwayRequestDTO {
		private String stationCode;
		private String direction;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SubwayResponseDTO {
		private ErrorMessage errorMessage;
		private List<RealtimeArrivalDTO.RealtimeArrivalResponseDTO> realtimeArrivalList;
	}
}
