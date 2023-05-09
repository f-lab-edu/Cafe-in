package kr.cafeIn.cafeorder.service;

import kr.cafeIn.cafeorder.exception.NotFoundException;
import kr.cafeIn.cafeorder.mapper.LikedMapper;
import kr.cafeIn.cafeorder.model.domain.Liked;
import kr.cafeIn.cafeorder.model.dto.request.LikeRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikedService {


  private final LikedMapper likedMapper;


  public void createLiked(LikeRequest likedReq, Long userId, Long cafeId) {
    Liked liked = Liked.builder()
        .userId(userId)
        .cafeId(cafeId)
        .likeStatus(likedReq.getLikeStatus())
        .build();

    likedMapper.insertLiked(liked);
  }


  public void updateLiked(LikeRequest likedReq, Long userId, Long likedId) {
    Liked liked = Liked.builder()
        .id(likedId)
        .userId(userId)
        .likeStatus(likedReq.getLikeStatus())
        .build();

    likedMapper.updateLiked(liked);
    likedMapper.updateUpdateAt(likedId);
  }


  public void deleteLiked(Long userId, Long likedId) {
    int deleteCount = likedMapper.deleteLiked(userId, likedId);
    if (deleteCount == 0) {
      throw new NotFoundException("선택한 좋아요를 찾을 수 없습니다");
    }
  }
}