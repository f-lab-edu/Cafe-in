package kr.cafeIn.cafeorder.model.domain;

import java.time.LocalDateTime;
import lombok.Builder;

/**
 * Review Model
 */


@Builder
public class Review {

  private Long id;
  private Long cafeId;
  private Long userId;
  private String description;
  private LocalDateTime createdAt; //생성날짜
  private LocalDateTime updatedAt; //수정날짜


}
