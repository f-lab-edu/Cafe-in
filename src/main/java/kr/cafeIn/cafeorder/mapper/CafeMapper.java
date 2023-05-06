package kr.cafeIn.cafeorder.mapper;

import java.util.List;
import java.util.Optional;
import kr.cafeIn.cafeorder.model.domain.Cafe;
import kr.cafeIn.cafeorder.model.dto.response.CafeInfoResponse;


public interface CafeMapper {

  void insertCafe(Cafe cafe);

  boolean isExistsCafe(String title);

  Optional<CafeInfoResponse> selectCafeById(Long cafeId);

  List<Cafe> selectCafeAll();

  void updateCafeById(Cafe cafe);

  void deleteCafe(Long id);

}
