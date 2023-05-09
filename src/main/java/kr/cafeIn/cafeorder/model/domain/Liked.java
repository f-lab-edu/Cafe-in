package kr.cafeIn.cafeorder.model.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Liked {


  private Long id;
  private Long userId;
  private Long cafeId;
  private Integer likeStatus; // 추천:1 비추천:0
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt = LocalDateTime.now();

}
