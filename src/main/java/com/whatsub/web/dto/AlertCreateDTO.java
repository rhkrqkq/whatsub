package com.whatsub.web.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AlertCreateDTO {
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AlertCreateRequestDTO {
		private String email;
		private String fcmToken;
		private String startStationId;
		private String endStationId;
		private LocalDateTime departureTime;
		private String direction;
		private Integer alertBefore;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AlertCreateResponseDTO {
		private String alertId;
		private String message;
		private boolean success;
	}
}
