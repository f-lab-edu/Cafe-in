package kr.cafeIn.cafeorder.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * MenuSave Dto.
 *
 * @since 1.0.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuSaveRequest {

	private String name;

	private Integer price;
}
