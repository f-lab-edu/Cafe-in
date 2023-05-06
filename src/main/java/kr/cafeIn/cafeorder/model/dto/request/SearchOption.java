package kr.cafeIn.cafeorder.model.dto.request;

import lombok.Getter;
import org.apache.ibatis.session.RowBounds;

/**
 * SearchOption 검색 옵션을 위한 클래스
 */

@Getter
public class SearchOption {

  private final Integer likeStatus;

  private final Long cafeId;

  private final Long userId;

  private final Integer offset;


  private final Integer limit;

  /**
   * SearchOptionBuilder 를 위한 생성자 page 번호를 입력받아 {@link RowBounds} 를 생성. LIMIT의 기본 값은 10. 페이지 번호를
   * 입력하지 않는 경우 첫 페이지로 조회. 0 이하의 값이 들어왔을 경우 예외 발생.
   *
   * @param likeStatus
   * @param cafeId
   * @param userId
   */


  public SearchOption(Integer likeStatus, Long cafeId, Long userId, Integer offset, Integer limit) {
    this.likeStatus = likeStatus;
    this.cafeId = cafeId;
    this.userId = userId;
    this.offset = offset;
    this.limit = limit;
  }


  public static SearchOptionBuilder builder() {

    return new SearchOptionBuilder();
  }


  /**
   * SearchOptionBuilder 를 위한 생성자 page 번호를 입력받아 {@link RowBounds} 를 생성. LIMIT의 기본 값은 10. 페이지 번호를
   * 입력하지 않는 경우 첫 페이지로 조회. 0 이하의 값이 들어왔을 경우 예외 발생.
   */

  public static class SearchOptionBuilder {

    private static final int DEFAULT_LIMIT = 10;

    private Integer likeStatus;
    private Long cafeId;
    private Long userId;
    private Integer offset;
    private Integer limit;


    public SearchOptionBuilder likeStatus(Integer likeStatus) {
      this.likeStatus = likeStatus;
      return this;

    }

    public SearchOptionBuilder cafeId(Long cafeId) {
      this.cafeId = cafeId;
      return this;
    }

    public SearchOptionBuilder userId(Long userId) {
      this.userId = userId;
      return this;
    }


    public SearchOption build() {
      return new SearchOption(likeStatus, cafeId, userId, offset, limit);
    }


    public SearchOptionBuilder page(Integer page) {
      if (page == null) {
        page = 1;
      }

      if (page <= 0) {
        throw new IllegalArgumentException("페이지 번호는 0보다 커야 합니다");
      }

      this.offset = (page - 1) * DEFAULT_LIMIT;
      this.limit = DEFAULT_LIMIT;
      return this;
    }


  }
}





