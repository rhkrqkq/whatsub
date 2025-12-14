package com.whatsub.web.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertListDTO {

	@Getter
	@Setter
	@AllArgsConstructor
	public static class AlertResponse {
		private String alertId;
		private String startStationName;
		private String endStationName;
		private String status;
		private LocalDateTime createdAt;
	}
}
