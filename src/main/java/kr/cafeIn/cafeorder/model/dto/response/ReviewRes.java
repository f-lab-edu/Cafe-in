package kr.cafeIn.cafeorder.model.dto.response;


import java.time.LocalDateTime;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewRes {

  @NotBlank
  private Long id;

  @NotBlank
  private String nickname;

  @NotBlank
  private String description;

  @Min(0)
  @Max(1)
  private int like;// 추천,비추천 보내기

  private LocalDateTime createdAt;

}
