package kr.cafeIn.cafeorder.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.cafeIn.cafeorder.exception.DuplicatedException;
import kr.cafeIn.cafeorder.mapper.CafeMapper;
import kr.cafeIn.cafeorder.model.domain.Cafe;
import kr.cafeIn.cafeorder.model.dto.request.CafeAddRequest;
import kr.cafeIn.cafeorder.model.dto.request.CafeUpdateRequest;
import kr.cafeIn.cafeorder.model.dto.response.CafeInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CafeServiceTest {

  @Mock
  CafeMapper cafeMapper;

  @InjectMocks
  CafeService cafeService;

  Cafe cafe;

  CafeAddRequest cafeAddRequest;

  CafeInfoResponse cafeInfoResponse;

  @BeforeEach
  public void addCafe() {
    cafe = Cafe.builder()
        .id(1L)
        .title("testCafe")
        .location("testLocation")
        .locationSetting("testLocationSetting")
        .tel("02-1234-1234")
        .latitude(50)
        .longitude(50)
        .build();

    cafeAddRequest = CafeAddRequest.builder()
        .title("testCafe")
        .location("testLocation")
        .locationSetting("testLocationSetting")
        .tel("02-1234-1234")
        .latitude(50)
        .longitude(50)
        .build();

    cafeInfoResponse = CafeInfoResponse.builder()
        .id(1L)
        .title("testCafe")
        .location("testLocation")
        .thumbnail("testThumbnail")
        .build();
  }

  @Test
  @DisplayName("카페추가에 성공합니다.")
  public void menuSaveTestWhenSuccess() {
    when(cafeMapper.isExistsCafe(cafeAddRequest.getTitle())).thenReturn(false);
    cafeService.addCafe(cafeAddRequest);
    verify(cafeMapper).insertCafe(any(Cafe.class));
  }

  @Test
  @DisplayName("카페추가에 실패합니다. : 중복된 카페")
  public void menuSaveTestWhenFail() {
    when(cafeMapper.isExistsCafe(cafeAddRequest.getTitle())).thenReturn(true);
    assertThrows(DuplicatedException.class, () -> cafeService.addCafe(cafeAddRequest));
    verify(cafeMapper).isExistsCafe(cafeAddRequest.getTitle());
  }

  @Test
  @DisplayName("카페 조회에 성공합니다.")
  public void selectCafeByIdTestWhenSuccess() {
    when(cafeMapper.selectCafeById(1L))
        .thenReturn(Optional.ofNullable(cafeInfoResponse));
    cafeService.getCafeInfo(1L);
    verify(cafeMapper).selectCafeById(1L);
  }

  @Test
  @DisplayName("카페 정보 수정에 성공합니다.")
  public void updateCafeByIdWhenSuccess() {
    CafeUpdateRequest requestDto = CafeUpdateRequest.builder().build();
    cafeService.updateCafeInfo(cafe.getId(), requestDto);
    verify(cafeMapper).updateCafeById(any(Cafe.class));
  }

  @Test
  @DisplayName("카페삭제에 성공합니다.")
  public void deleteCafeWhenSuccess() {
    cafeService.deleteCafe(cafe.getId());
    verify(cafeMapper).deleteCafe(cafe.getId());
  }


}
