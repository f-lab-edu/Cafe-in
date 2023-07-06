package kr.cafeIn.cafeorder.model.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Order {

	private Long id;

	private Long userId;

	private Long cafeId;

	private Long tableId;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
