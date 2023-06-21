package kr.cafeIn.cafeorder.model.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import kr.cafeIn.cafeorder.exception.DuplicatedException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CafeRequest {

	private Long id;

	private String title;

	private String location;

	private String locationSetting;

	private String tel;

	private Integer latitude;

	private Integer longitude;

	private String thumbnail;

}
