package kr.cafeIn.cafeorder.model.domain;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

  private Long id;
  private String email;
  private String password;
  private String nickname;
  private Integer grade; //등급
  private Integer point; //포인트
  private LocalDateTime createdAt; //생성날짜
  private LocalDateTime updatedAt; //수정날짜
  private LocalDateTime withdrawnAt;//탈퇴날짜 ,null

}
