package kr.cafeIn.cafeorder.service;

import java.util.List;
import kr.cafeIn.cafeorder.exception.NotFoundException;
import kr.cafeIn.cafeorder.mapper.ReviewMapper;
import kr.cafeIn.cafeorder.model.domain.Review;
import kr.cafeIn.cafeorder.model.dto.request.ReviewReq;
import kr.cafeIn.cafeorder.model.dto.request.SearchOption;
import kr.cafeIn.cafeorder.model.dto.response.PageInfo;
import kr.cafeIn.cafeorder.model.dto.response.ReviewRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {


  private final ReviewMapper reviewMapper;

  /**
   * 카페 리뷰 조회
   */
  @Transactional(readOnly = true)
  @Cacheable(value = "cafes")
  public PageInfo<ReviewRes> getCafeReview(SearchOption searchOption) {
    List<ReviewRes> reviewResList = reviewMapper.selectReviewsWithLikeStatus(searchOption);

    Long totalCount = reviewMapper.countReviewsWithLikeStatus(searchOption);

    return new PageInfo<>(totalCount, reviewResList);
  }


  /**
   * 리뷰 생성
   *
   * @param reviewReq
   * @param userId
   * @param cafeId
   */
  @CacheEvict(value = "cafes", allEntries = true)
  public void createReview(ReviewReq reviewReq, Long userId, Long cafeId) {
    Review review = Review.builder().userId(userId).cafeId(cafeId)
        .description(reviewReq.getDescription()).build();
    log.info("review1:{}", review);
    reviewMapper.insertReview(review);
    log.info("review2:{}", review);
  }

  /**
   * 리뷰 업데이트
   *
   * @param reviewReq
   * @param userId
   * @param reviewId
   */
  @CacheEvict(value = "cafes", allEntries = true)
  public void updateReview(ReviewReq reviewReq, Long userId, Long reviewId) {
    Review review = Review.builder().id(reviewId).userId(userId)
        .description(reviewReq.getDescription()).build();

    int updateCount = reviewMapper.updateReview(review);
    if (updateCount == 0) {
      throw new NotFoundException("존재하지 않는 리뷰입니다");
    }

  }

  /**
   * 리뷰 삭제
   *
   * @param userId
   * @param reviewId
   */


  @CacheEvict(value = "cafes", allEntries = true)
  public void deleteReview(Long userId, Long reviewId) {
    int deleteCount = reviewMapper.deleteReview(userId, reviewId);
    if (deleteCount == 0) {
      throw new NotFoundException("선택한 리뷰를 찾을 수 없습니다");
    }
  }


}