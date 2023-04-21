package kr.cafeIn.cafeorder.model.dto.request;

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
public class CafeUpdateRequest {

	private String title;

	private String location;

	private String locationSetting;

	private String tel;

	private Integer latitude;

	private Integer longitude;

	private String thumbnail;
}
