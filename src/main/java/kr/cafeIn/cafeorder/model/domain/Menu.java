package kr.cafeIn.cafeorder.model.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Cafe Menu Model.
 *
 * @since 1.0.0
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Menu {

	private Long id;

	private Long cafeId;

	private String name;

	private Integer price;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

}
