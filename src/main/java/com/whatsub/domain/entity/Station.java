package com.whatsub.domain.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = "station")
@Getter
@Setter
@NoArgsConstructor
public class Station {
	@Id
	private ObjectId id;
	private ObjectId lineId;
	private String name;
	private String code;
	private Integer sequenceOrder;
	private LocalDateTime createdAt;
}
