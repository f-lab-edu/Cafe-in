package kr.cafeIn.cafeorder.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * MenuUpdate Dto.
 *
 * @since 1.0.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class MenuUpdateRequest {

	private Long id;

	private String name;

	private Integer price;
}
