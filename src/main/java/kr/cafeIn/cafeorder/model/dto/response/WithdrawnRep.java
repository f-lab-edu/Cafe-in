package kr.cafeIn.cafeorder.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WithdrawnRep {

  private Long id;
  private String email;
  private LocalDateTime withdrawnAt;// temporary property
}
