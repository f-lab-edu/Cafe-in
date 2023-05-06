package kr.cafeIn.cafeorder.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import kr.cafeIn.cafeorder.exception.NotFoundException;
import kr.cafeIn.cafeorder.mapper.LikedMapper;
import kr.cafeIn.cafeorder.model.domain.Liked;
import kr.cafeIn.cafeorder.model.dto.request.LikedReq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class LikedServiceTest {


  @InjectMocks
  private LikedService likedService;

  @Mock
  private LikedMapper likedMapper;


  @Test
  @DisplayName("좋아요 생성 - 성공")
  public void testCreateLiked() {
    // given
    Long userId = 1L;
    Long cafeId = 2L;
    LikedReq likedReq = new LikedReq(1);

    // when
    likedService.createLiked(likedReq, userId, cafeId);

    // then
    verify(likedMapper).insertLiked(any(Liked.class));
  }

  @Test
  @DisplayName("좋아요 수정 - 성공")
  public void testUpdateLiked() {
    // given
    Long userId = 1L;
    Long likedId = 2L;
    LikedReq likedReq = new LikedReq(1);
    Liked liked = Liked.builder().id(likedId).userId(userId).likeStatus(1).build();

    // when
    when(likedMapper.updateLiked(any(Liked.class))).thenReturn(1);
    likedService.updateLiked(likedReq, userId, likedId);

    // then
    verify(likedMapper).updateLiked(any(Liked.class));
  }

  @Test
  @DisplayName("좋아요 수정 - 실패 : NotFoundException")
  public void testUpdateLikedNotFoundException() {
    // given
    Long userId = 1L;
    Long likedId = 2L;
    LikedReq likedReq = new LikedReq(0);
    Liked liked = Liked.builder().id(likedId).userId(userId).likeStatus(0).build();

    // when
    when(likedMapper.updateLiked(any(Liked.class))).thenReturn(0);

    // then
    assertThrows(NotFoundException.class,
        () -> likedService.updateLiked(likedReq, userId, likedId));
    verify(likedMapper).updateLiked(any(Liked.class));
  }

  @Test
  @DisplayName("좋아요 삭제 - 성공")
  public void testDeleteLiked() {
    // given
    Long userId = 1L;
    Long likedId = 2L;

    // when
    when(likedMapper.deleteLiked(userId, likedId)).thenReturn(1);
    likedService.deleteLiked(userId, likedId);

    // then
    verify(likedMapper).deleteLiked(userId, likedId);
  }

  @Test
  @DisplayName("줗아요 삭제에 실패합니다. : 해당 유저가 아닌 경우.")
  public void deleteReviewWhenFail() {
    when(likedMapper.deleteLiked(1L, 2L)).thenReturn(0);
    assertThrows(NotFoundException.class, () -> likedService.deleteLiked(1L, 2L));
    verify(likedMapper).deleteLiked(1L, 2L);
  }

}
