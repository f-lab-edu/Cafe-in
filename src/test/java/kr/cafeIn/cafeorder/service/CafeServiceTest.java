package kr.cafeIn.cafeorder.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;
import kr.cafeIn.cafeorder.exception.DuplicatedException;
import kr.cafeIn.cafeorder.mapper.CafeMapper;
import kr.cafeIn.cafeorder.mapper.CafeTableMapper;
import kr.cafeIn.cafeorder.model.domain.Cafe;
import kr.cafeIn.cafeorder.model.dto.request.CafeRequest;
import kr.cafeIn.cafeorder.model.dto.request.SearchTimeRequest;
import kr.cafeIn.cafeorder.model.dto.response.CafeDetailResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CafeServiceTest {

	Cafe cafe;

	@InjectMocks
	CafeService cafeService;

	@Mock
	CafeMapper cafeMapper;

	@Mock
	CafeTableMapper cafeTableMapper;

	CafeRequest cafeRequest;

	@BeforeEach
	public void createCafe() {
		cafe = Cafe.builder()
			.id(1L)
			.title("testCafe")
			.location("testLocation")
			.locationSetting("testLocationSetting")
			.tel("02-1234-1234")
			.latitude(50)
			.longitude(50)
			.build();

		cafeRequest = CafeRequest.builder()
			.title("testCafe")
			.location("testLocation")
			.locationSetting("testLocationSetting")
			.tel("02-1234-1234")
			.latitude(50)
			.longitude(50)
			.build();

	}

	@Test
	@DisplayName("카페추가에 성공합니다.")
	public void createCafeTestWhenSuccess() {
		when(cafeMapper.isExistsCafe(cafeRequest.getTitle())).thenReturn(false);
		cafeService.createCafe(cafeRequest);
		verify(cafeMapper).createCafe(any(Cafe.class));
	}

	@Test
	@DisplayName("카페추가에 실패합니다. : 중복된 카페")
	public void createCafeTestWhenFail() {
		when(cafeMapper.isExistsCafe(cafeRequest.getTitle())).thenReturn(true);
		assertThrows(DuplicatedException.class, () -> cafeService.createCafe(cafeRequest));
		verify(cafeMapper).isExistsCafe(cafeRequest.getTitle());
	}

	@Test
	@DisplayName("카페 상세 조회에 성공합니다.")
	public void getCafeTestWhenSuccess() {
		when(cafeMapper.selectCafeDetailById(anyLong()))
			.thenReturn(Optional.of(new CafeDetailResponse()));
		when(cafeTableMapper.selectCafeTableUseInfo(anyLong(), any(LocalDateTime.class)))
			.thenReturn(any());
		cafeService.getCafe(1L, new SearchTimeRequest(LocalDateTime.now()));
		verify(cafeMapper).selectCafeDetailById(anyLong());
		verify(cafeTableMapper).selectCafeTableUseInfo(anyLong(), any(LocalDateTime.class));
	}

	@Test
	@DisplayName("카페 정보 수정에 성공합니다.")
	public void updateCafeTestWhenSuccess() {
		CafeRequest requestDto = CafeRequest.builder().build();
		cafeService.updateCafe(requestDto, cafe.getId());
		verify(cafeMapper).updateCafe(any(Cafe.class));
	}

	@Test
	@DisplayName("카페삭제에 성공합니다.")
	public void deleteCafeTestWhenSuccess() {
		cafeService.deleteCafe(cafe.getId());
		verify(cafeMapper).deleteCafe(cafe.getId());
	}

}
