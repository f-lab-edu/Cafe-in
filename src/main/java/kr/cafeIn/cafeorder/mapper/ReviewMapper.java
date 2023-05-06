package kr.cafeIn.cafeorder.mapper;


import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ReviewMapper {

  void insertReview(Review review);

  int updateReview(Review review);

  int deleteReview(@Param("userId") Long userId, @Param("reviewId") Long reviewId);


  // 해당 카페 기준 목록 조회
  List<ReviewRes> selectReviewsWithLikeStatus(SearchOption searchOption);

  Long countReviewsWithLikeStatus(SearchOption searchOption);

}
