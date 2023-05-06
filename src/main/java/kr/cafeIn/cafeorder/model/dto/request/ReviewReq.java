package kr.cafeIn.cafeorder.model.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Review Request Dto.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewReq {

  @NotBlank
  private String description;


}
