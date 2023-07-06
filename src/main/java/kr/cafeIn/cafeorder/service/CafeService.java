package kr.cafeIn.cafeorder.service;

import java.util.ArrayList;
import java.util.List;
import kr.cafeIn.cafeorder.exception.DuplicatedException;
import kr.cafeIn.cafeorder.exception.NotFoundException;
import kr.cafeIn.cafeorder.mapper.CafeMapper;
import kr.cafeIn.cafeorder.mapper.CafeTableMapper;
import kr.cafeIn.cafeorder.model.domain.Cafe;
import kr.cafeIn.cafeorder.model.domain.CafeTable;
import kr.cafeIn.cafeorder.model.dto.request.CafeRequest;
import kr.cafeIn.cafeorder.model.dto.request.SearchTimeRequest;
import kr.cafeIn.cafeorder.model.dto.request.TableRequest;
import kr.cafeIn.cafeorder.model.dto.response.CafeDetailResponse;
import kr.cafeIn.cafeorder.model.dto.response.CafeTableUseInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CafeService {

	private final CafeMapper cafeMapper;

	private final CafeTableMapper cafeTableMapper;

	/**
	 * 카페 등록.
	 *
	 * @param cafeRequest 카페 등록 DTO
	 * @since 1.0.0
	 */

	public void createCafe(CafeRequest cafeRequest) {
		if (isExistsCafe(cafeRequest.getTitle())) {
			throw new DuplicatedException("This cafe already exists.");
		}

		Cafe cafe = Cafe.builder()
			.title(cafeRequest.getTitle())
			.location(cafeRequest.getLocation())
			.locationSetting(cafeRequest.getLocationSetting())
			.tel(cafeRequest.getTel())
			.latitude(cafeRequest.getLatitude())
			.longitude(cafeRequest.getLongitude())
			.build();

		cafeMapper.createCafe(cafe);
	}

	@Transactional(readOnly = true)
	public boolean isExistsCafe(String title) {

		return cafeMapper.isExistsCafe(title);
	}

	/**
	 * 카페 디테일 조회.
	 *
	 * @param cafeId 			카페 dto.
	 * @param searchTimeRequest 검색 시간 dto.
	 * @since 1.0.0
	 */

	public CafeDetailResponse getCafe(Long cafeId, SearchTimeRequest searchTimeRequest) {
		CafeDetailResponse cafeRes = cafeMapper.selectCafeDetailById(cafeId)
			.orElseThrow(() -> new NotFoundException("Select not found cafe."));

		List<CafeTableUseInfoResponse> table = cafeTableMapper.selectCafeTableUseInfo(cafeId,
			searchTimeRequest.getSearchTime());

		cafeRes.setTable(table);
		return cafeRes;
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
	 * @param cafeId         카페 ID.
	 * @param cafeRequest 카페수정 DTO.
	 * @since 1.0.0
	 */

	public void updateCafe(CafeRequest cafeRequest, Long cafeId) {

		Cafe cafe = Cafe.builder()
			.id(cafeId)
			.title(cafeRequest.getTitle())
			.location(cafeRequest.getLocation())
			.locationSetting(cafeRequest.getLocationSetting())
			.tel(cafeRequest.getTel())
			.latitude(cafeRequest.getLatitude())
			.longitude(cafeRequest.getLongitude())
			.build();

		cafeMapper.updateCafe(cafe);
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
