package kr.cafeIn.cafeorder.service;

import java.util.List;
import kr.cafeIn.cafeorder.exception.DuplicatedException;
import kr.cafeIn.cafeorder.exception.NotFoundException;
import kr.cafeIn.cafeorder.mapper.CafeMapper;
import kr.cafeIn.cafeorder.model.domain.Cafe;
import kr.cafeIn.cafeorder.model.dto.request.CafeAddRequest;
import kr.cafeIn.cafeorder.model.dto.request.CafeUpdateRequest;
import kr.cafeIn.cafeorder.model.dto.response.CafeInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CafeService {

  private final CafeMapper cafeMapper;

  /**
   * 카페 등록.
   *
   * @param cafeAddRequest 카페 등록 DTO
   * @since 1.0.0
   */

  public void addCafe(CafeAddRequest cafeAddRequest) {
    if (isExistsCafe(cafeAddRequest.getTitle())) {
      throw new DuplicatedException("This cafe already exists.");
    }

    Cafe cafe = Cafe.builder()
        .title(cafeAddRequest.getTitle())
        .location(cafeAddRequest.getLocation())
        .locationSetting(cafeAddRequest.getLocationSetting())
        .tel(cafeAddRequest.getTel())
        .latitude(cafeAddRequest.getLatitude())
        .longitude(cafeAddRequest.getLongitude())
        .build();
    cafeMapper.insertCafe(cafe);
  }

  @Transactional(readOnly = true)
  public boolean isExistsCafe(String title) {
    return cafeMapper.isExistsCafe(title);
  }

  /**
   * id에 해당하는 카페 정보 조회.
   *
   * @param id 카페 dto.
   * @since 1.0.0
   */

  public CafeInfoResponse getCafeInfo(Long id) {

    return cafeMapper.selectCafeById(id)
        .orElseThrow(() -> new NotFoundException("Select not found menu"));
  }

  /**
   * 카페 정보 전체 조회.
   *
   * @since 1.0.0
   */

  public List<Cafe> selectCafeAll() {
    List<Cafe> cafeList = cafeMapper.selectCafeAll();

    return cafeList;
  }

  /**
   * id에 해당하는 카페 정보 수정.
   *
   * @param cafeId            카페 ID.
   * @param cafeUpdateRequest 카페수정 DTO.
   * @since 1.0.0
   */

  public void updateCafeInfo(Long cafeId, CafeUpdateRequest cafeUpdateRequest) {
    Cafe cafe = Cafe.builder()
        .id(cafeId)
        .title(cafeUpdateRequest.getTitle())
        .location(cafeUpdateRequest.getLocation())
        .locationSetting(cafeUpdateRequest.getLocationSetting())
        .tel(cafeUpdateRequest.getTel())
        .latitude(cafeUpdateRequest.getLatitude())
        .longitude(cafeUpdateRequest.getLongitude())
        .build();

    cafeMapper.updateCafeById(cafe);
  }

  /**
   * id에 해당하는 카페 삭제.
   *
   * @param cafeId 카페 ID.
   * @since 1.0.0
   */

  public void deleteCafe(Long cafeId) {
    cafeMapper.deleteCafe(cafeId);
  }
}
