package kr.cafeIn.cafeorder.model.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class LikedReq {


  @Min(0)
  @Max(1)
  private Integer likeStatus;


}
