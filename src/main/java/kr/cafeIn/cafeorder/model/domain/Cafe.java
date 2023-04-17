package kr.cafeIn.cafeorder.model.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cafe {

	private Long id;

	private String title;

	private String location;

	private String locationSetting;

	private String tel;

	private Integer latitude;

	private Integer longitude;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
