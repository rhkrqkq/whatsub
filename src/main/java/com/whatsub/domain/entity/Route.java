package com.whatsub.domain.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "route")
@Getter
@Setter
@NoArgsConstructor
public class Route {
	@Id
	private ObjectId id;

	private ObjectId startStationId;
	private ObjectId endStationId;
	private Integer travelTime;
	private LocalDateTime createdAt;
}
