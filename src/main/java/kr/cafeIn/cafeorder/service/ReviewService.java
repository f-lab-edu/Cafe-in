package kr.cafeIn.cafeorder.service;

import java.util.List;
import kr.cafeIn.cafeorder.exception.NotFoundException;
import kr.cafeIn.cafeorder.mapper.ReviewMapper;
import kr.cafeIn.cafeorder.model.domain.Review;
import kr.cafeIn.cafeorder.model.dto.request.ReviewRequest;
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

  @Transactional(readOnly = true)
  @Cacheable(value = "cafes")
  public PageInfo<ReviewRes> getCafeReview(SearchOption searchOption) {
    List<ReviewRes> reviewResList = reviewMapper.selectReviewsWithLikeStatus(searchOption);

    Long totalCount = reviewMapper.countReviewsWithLikeStatus(searchOption);

    return new PageInfo<>(totalCount, reviewResList);
  }


  @CacheEvict(value = "cafes", allEntries = true)
  public void createReview(ReviewRequest reviewReq, Long userId, Long cafeId) {
    Review review = Review.builder()
        .userId(userId)
        .cafeId(cafeId)
        .description(reviewReq.getDescription())
        .build();

    reviewMapper.insertReview(review);

  }


  @CacheEvict(value = "cafes", allEntries = true)
  public void updateReview(ReviewRequest reviewReq, Long userId, Long reviewId) {
    Review review = Review.builder()
        .id(reviewId)
        .userId(userId)
        .description(reviewReq.getDescription())
        .build();

    reviewMapper.updateUpdateAt(reviewId);
    reviewMapper.updateReview(review);

  }


  @CacheEvict(value = "cafes", allEntries = true)
  public void deleteReview(Long userId, Long reviewId) {
    int deleteCount = reviewMapper.deleteReview(userId, reviewId);
    if (deleteCount == 0) {
      throw new NotFoundException("선택한 리뷰를 찾을 수 없습니다");
    }
  }
}