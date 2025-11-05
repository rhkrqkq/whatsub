package com.whatsub.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class RealtimeArrivalDTO {
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RealtimeArrivalRequestDTO {
		private String subwayId;
		private String direction;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class RealtimeArrivalResponseDTO {
		private String subwayId;
		private String direction;
		private String trainLineNum;
		private String stationId;
		private String stationNum;
		private String stationName;
		private String trainNum;
		private String message;
		private String messageDetail;
		private String arrivalCode;
		private String receivedAt;
	}
}
