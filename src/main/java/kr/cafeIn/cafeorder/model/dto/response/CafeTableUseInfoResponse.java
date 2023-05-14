package kr.cafeIn.cafeorder.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CafeTableUseInfoResponse {

	private Long id;

	private Integer tableNumber;

	private Integer capacity;

	private Boolean currentUse;
}
