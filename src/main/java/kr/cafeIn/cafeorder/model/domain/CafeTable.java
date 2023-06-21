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
public class CafeTable {

	private Long id;

	private Long tableId;

	private Long cafeId;

	private Integer tableNumber;

	private Integer capacity;

	private String currentUse;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
