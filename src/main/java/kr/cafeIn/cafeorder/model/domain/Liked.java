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


  Long id;
  Long userId;
  Long cafeId;
  Integer likeStatus; //좋아요 상태  좋아요: 1
  LocalDateTime createdAt;
  LocalDateTime updatedAt;

}
