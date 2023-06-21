package kr.cafeIn.cafeorder.mapper;

import java.util.List;
import java.util.Optional;
import kr.cafeIn.cafeorder.model.domain.Cafe;
import kr.cafeIn.cafeorder.model.dto.response.CafeDetailResponse;


public interface CafeMapper {

	void createCafe(Cafe cafe);

	boolean isExistsCafe(String title);

	Optional<Cafe> selectCafeById(Long cafeId);

	Optional<CafeDetailResponse> selectCafeDetailById(Long cafeId);

	List<Cafe> selectCafeAll();

	void updateCafe(Cafe cafe);

	void deleteCafe(Long id);

}
