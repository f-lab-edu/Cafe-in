package kr.cafeIn.cafeorder.model.dto.response;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CafeDetailResponse {

	private Long id;
	private String title;
	private String location;
	private String locationSetting;
	private String thumbnail;
	private String tel;
	private Integer latitude;
	private Integer longitude;
	private List<CafeTableUseInfoResponse> table;

}
