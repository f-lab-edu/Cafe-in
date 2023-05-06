package kr.cafeIn.cafeorder.service;

import kr.cafeIn.cafeorder.exception.NotFoundException;
import kr.cafeIn.cafeorder.mapper.LikedMapper;
import kr.cafeIn.cafeorder.model.domain.Liked;
import kr.cafeIn.cafeorder.model.dto.request.LikedReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikedService {


  private final LikedMapper likedMapper;


  /**
   * 좋아요 생성
   *
   * @param likedReq
   * @param userId
   * @param cafeId
   */

  public void createLiked(LikedReq likedReq, Long userId, Long cafeId) {
    Liked liked = Liked.builder().userId(userId).cafeId(cafeId).likeStatus(likedReq.getLikeStatus())
        .build();
    log.info("liked:{}", liked);

    likedMapper.insertLiked(liked);
  }


  /**
   * 좋아요 수정
   *
   * @param likedReq
   * @param userId
   * @param likedId
   */

  public void updateLiked(LikedReq likedReq, Long userId, Long likedId) {
    Liked liked = Liked.builder().id(likedId).userId(userId).likeStatus(likedReq.getLikeStatus())
        .build();

    int updateCount = likedMapper.updateLiked(liked);
    if (updateCount == 0) {
      throw new NotFoundException("기존 추천/비추천 데이터가 없습니다");
    }

  }


  public void deleteLiked(Long userId, Long likedId) {
    int deleteCount = likedMapper.deleteLiked(userId, likedId);
    if (deleteCount == 0) {
      throw new NotFoundException("선택한 좋아요를 찾을 수 없습니다");
    }
  }


}