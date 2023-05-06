package kr.cafeIn.cafeorder.model.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * PageInfo. 페이징 처리 후 담아서 보낼 DTO.
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class PageInfo<E> {

  private Long totalCount;
  private List<E> list;
}

