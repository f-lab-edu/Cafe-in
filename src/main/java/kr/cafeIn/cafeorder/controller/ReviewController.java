package kr.cafeIn.cafeorder.controller;


import javax.validation.Valid;
import kr.cafeIn.cafeorder.annotation.CurrentUserId;
import kr.cafeIn.cafeorder.annotation.LoginCheck;
import kr.cafeIn.cafeorder.model.dto.request.ReviewReq;
import kr.cafeIn.cafeorder.model.dto.request.SearchOption;
import kr.cafeIn.cafeorder.model.dto.response.PageInfo;
import kr.cafeIn.cafeorder.model.dto.response.ReviewRes;
import kr.cafeIn.cafeorder.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/cafes")
@RequiredArgsConstructor
@RestController
public class ReviewController {

  private final ReviewService reviewService;


  /**
   * 카페 리뷰 조회하기
   *
   * @param cafeId
   * @param likeStatus
   * @param page
   * @return
   */
  @GetMapping("/{id}/reviews")
  @ResponseStatus(HttpStatus.CREATED)
  public PageInfo<ReviewRes> getCafeReviews(@PathVariable("id") Long cafeId,
      @RequestParam(required = false) Integer likeStatus,
      @RequestParam(required = false) Integer page) {
    SearchOption searchOption = SearchOption.builder().likeStatus(likeStatus).cafeId(cafeId)
        .page(page).build();

    return reviewService.getCafeReview(searchOption);
  }


  /**
   * 해당 사용자 리뷰 목록 조회하기
   *
   * @param userId
   * @param cafeId
   * @param likeStatus
   * @param page
   * @return
   */

  @GetMapping("/user/reviews")
  @LoginCheck
  @ResponseStatus(HttpStatus.CREATED)
  public PageInfo<ReviewRes> getUserCafeReviews(@CurrentUserId Long userId,
      @RequestParam(required = false) Long cafeId,
      @RequestParam(required = false) Integer likeStatus,
      @RequestParam(required = false) Integer page) {
    SearchOption searchOption = SearchOption.builder().likeStatus(likeStatus).cafeId(cafeId)
        .userId(userId).page(page).build();

    return reviewService.getCafeReview(searchOption);
  }


  /**
   * 리뷰 등록하기
   *
   * @param userId    유저 ID
   * @param cafeId    카페 ID
   * @param reviewReq 리뷰 DTO
   */


  @PostMapping("/{id}/reviews")
  @LoginCheck
  @ResponseStatus(HttpStatus.CREATED)
  public void createReview(@CurrentUserId Long userId, @PathVariable("id") Long cafeId,
      @Valid @RequestBody ReviewReq reviewReq) {
    log.info("리뷰");
    reviewService.createReview(reviewReq, userId, cafeId);
    log.info("리뷰");
  }


  /**
   * 리뷰 수정하기
   *
   * @param userId
   * @param cafeId
   * @param reviewId
   * @param reviewReq
   */
  @PutMapping("/{id}/reviews/{reviewId}")
  @LoginCheck
  @ResponseStatus(HttpStatus.OK)
  public void updateReview(@CurrentUserId Long userId, @PathVariable("id") Long cafeId,
      @PathVariable("reviewId") Long reviewId, @RequestBody ReviewReq reviewReq) {
    reviewService.updateReview(reviewReq, userId, reviewId);
  }

  /**
   * 리뷰 삭제하기.
   *
   * @param userId   유저 ID
   * @param cafeId   카페 ID
   * @param reviewId 리뷰 ID
   * @since 1.0.0
   */
  @DeleteMapping("/{id}/reviews/{reviewId}")
  @LoginCheck
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteReview(@CurrentUserId Long userId, @PathVariable("id") Long cafeId,
      @PathVariable("reviewId") Long reviewId) {
    reviewService.deleteReview(userId, reviewId);
  }


}
