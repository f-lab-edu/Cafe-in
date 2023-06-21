package kr.cafeIn.cafeorder.model.dto.request;

import javax.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableRequest {

	private Long cafeId;

	@Min(1)
	private Integer tableNumber;

	@Min(1)
	private Integer capacity;
}
