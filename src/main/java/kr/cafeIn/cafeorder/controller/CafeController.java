package kr.cafeIn.cafeorder.controller;

import java.util.List;
import javax.validation.Valid;
import kr.cafeIn.cafeorder.model.domain.Cafe;
import kr.cafeIn.cafeorder.model.dto.request.CafeAddRequest;
import kr.cafeIn.cafeorder.model.dto.request.CafeUpdateRequest;
import kr.cafeIn.cafeorder.model.dto.response.CafeInfoResponse;
import kr.cafeIn.cafeorder.service.CafeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cafes")
@RequiredArgsConstructor
@RestController
public class CafeController {

  private final CafeService cafeService;

  /**
   * 카페 등록.
   *
   * @param cafeAddRequest 카페등록 입력 정보
   * @since 1.0.0
   */

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public void addCafe(@Valid @RequestBody CafeAddRequest cafeAddRequest) {
    cafeService.addCafe(cafeAddRequest);
  }

  /**
   * 카페 조회.
   *
   * @param id 카페명.
   * @since 1.0.0
   */

  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public CafeInfoResponse getCafeInfo(@PathVariable("id") Long id) {
    return cafeService.getCafeInfo(id);
  }

  /**
   * 카페 전체 조회.
   *
   * @since 1.0.0
   */

  @GetMapping("/page")
  @ResponseStatus(HttpStatus.CREATED)
  public List<Cafe> getCafeAll() {
    return cafeService.selectCafeAll();
  }

  /**
   * 카페 정보 수정.
   *
   * @param cafeId 카페 ID.
   * @since 1.0.0
   */

  @PutMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  public void updateCafeInfo(@PathVariable("id") Long cafeId,
      @RequestBody CafeUpdateRequest cafeUpdateRequest) {
    cafeService.updateCafeInfo(cafeId, cafeUpdateRequest);
  }

  /**
   * 카페 삭제.
   *
   * @param cafeId 카페 ID.
   * @since 1.0.0
   */

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.CREATED)
  public void deleteCafe(@PathVariable("id") Long cafeId) {
    cafeService.deleteCafe(cafeId);
  }
}
