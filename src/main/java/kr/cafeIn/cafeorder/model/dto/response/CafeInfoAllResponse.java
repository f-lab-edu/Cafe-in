package kr.cafeIn.cafeorder.model.dto.response;

import java.time.LocalDateTime;
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
public class CafeInfoAllResponse {

	private Long id;
	//	private Long cafe_id; 임시로 빼기
	private String title;

	private String location;

	private String locationSetting;

	private String tel;

	private Integer latitude;

	private Integer longitude;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
