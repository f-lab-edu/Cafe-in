package kr.cafeIn.cafeorder.model.dto.response;

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
public class CafeInfoResponse {

	private Long id;
	//	private Long cafe_id; 임시로 빼기
	private String title;

	private String location;

	private String thumbnail;
}
