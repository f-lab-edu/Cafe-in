package kr.cafeIn.cafeorder.controller;

import java.time.LocalDateTime;
import java.util.List;
import javax.validation.Valid;
import kr.cafeIn.cafeorder.annotation.CurrentUserId;
import kr.cafeIn.cafeorder.annotation.LoginCheck;
import kr.cafeIn.cafeorder.annotation.OwnerCheck;
import kr.cafeIn.cafeorder.model.domain.Cafe;
import kr.cafeIn.cafeorder.model.dto.request.CafeRequest;
import kr.cafeIn.cafeorder.model.dto.request.MenuRequest;
import kr.cafeIn.cafeorder.model.dto.request.SearchTimeRequest;
import kr.cafeIn.cafeorder.model.dto.request.TableRequest;
import kr.cafeIn.cafeorder.model.dto.response.CafeDetailResponse;
import kr.cafeIn.cafeorder.service.CafeService;
import kr.cafeIn.cafeorder.service.CafeTableService;
import kr.cafeIn.cafeorder.service.MenuService;
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

/**
 * CafeIn Controller.
 *
 * @since 1.0.0
 */

@RequestMapping("/cafes")
@RequiredArgsConstructor
@RestController
public class CafeInController {

	private final CafeService cafeService;
	private final MenuService menuService;
	private final CafeTableService cafeTableService;

	@GetMapping("/page")
	@LoginCheck
	@ResponseStatus(HttpStatus.OK)
	public List<Cafe> getCafeAll() {
		return cafeService.selectCafeAll();
	}

	// 카페 상세 조회하기.
	@GetMapping("/{cafeId}")
	@LoginCheck
	@ResponseStatus(HttpStatus.OK)
	public CafeDetailResponse getCafe(@CurrentUserId Long userId,
		@PathVariable("cafeId") Long cafeId,
		@RequestBody(required = false) SearchTimeRequest searchTimeRequest) {
		if (searchTimeRequest == null) {
			searchTimeRequest = new SearchTimeRequest(LocalDateTime.now());
		}

		return cafeService.getCafe(cafeId, searchTimeRequest);
	}

	// 카페 추가하기
	@PostMapping
	@LoginCheck
	@OwnerCheck
	@ResponseStatus(HttpStatus.CREATED)
	public void createCafe(@Valid @RequestBody CafeRequest cafeRequest) {
		cafeService.createCafe(cafeRequest);
	}

	// 카페 수정하기
	@PutMapping("/{cafeId}")
	@LoginCheck
	@OwnerCheck
	@ResponseStatus(HttpStatus.OK)
	public void updateCafe(@CurrentUserId Long userId,
		@PathVariable("cafeId") Long cafeId,
		@RequestBody CafeRequest cafeRequest) {
		cafeService.updateCafe(cafeRequest, cafeId);
	}

	// 카페 삭제하기
	@DeleteMapping("/{cafeId}")
	@LoginCheck
	@OwnerCheck
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteCafe(@CurrentUserId Long userId,
		@PathVariable("cafeId") Long cafeId) {
		cafeService.deleteCafe(cafeId);
	}

	// 테이블 추가하기
	@PostMapping("/{cafeId}/reservations")
	@LoginCheck
	@OwnerCheck
	@ResponseStatus(HttpStatus.CREATED)
	public void createTable(@CurrentUserId Long userId,
		@PathVariable("cafeId") Long cafeId, @RequestBody TableRequest tableRequest) {
		cafeTableService.createTable(tableRequest, cafeId);
	}

	// 테이블 수정하기
	@PutMapping("/{cafeId}/reservations/{tableId}")
	@LoginCheck
	@OwnerCheck
	@ResponseStatus(HttpStatus.OK)
	public void updateTable(@CurrentUserId Long userId,
		@PathVariable("cafeId") Long cafeId,
		@PathVariable("tableId") Long tableId, @RequestBody TableRequest tableRequest) {
		cafeTableService.updateTable(tableRequest, tableId, cafeId);
	}

	// 테이블 삭제하기
	@DeleteMapping("/{cafeId}/reservations/{tableId}")
	@LoginCheck
	@OwnerCheck
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteTable(@CurrentUserId Long userId,
		@PathVariable("cafeId") Long cafeId, @PathVariable("tableId") Long tableId) {
		cafeTableService.deleteTable(tableId);
	}

	// 메뉴 추가하기
	@PostMapping("/{cafeId}/menu")
	@LoginCheck
	@OwnerCheck
	@ResponseStatus(HttpStatus.CREATED)
	public void createMenu(@CurrentUserId Long userId,
		@PathVariable("cafeId") Long cafeId, @RequestBody MenuRequest menuRequest) {
		menuService.createMenu(menuRequest, cafeId);
	}

	// 메뉴 수정하기
	@PutMapping("/{cafeId}/menu/{menuId}")
	@LoginCheck
	@OwnerCheck
	@ResponseStatus(HttpStatus.OK)
	public void updateMenu(@CurrentUserId Long userId,
		@PathVariable("cafeId") Long cafeId,
		@PathVariable("menuId") Long menuId, @RequestBody MenuRequest menuRequest) {
		menuService.updateMenu(menuRequest, menuId, cafeId);
	}

	// 메뉴 삭제하기
	@DeleteMapping("/{cafeId}/menu/{menuId}")
	@LoginCheck
	//@OwnerCheck
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteMenu(@CurrentUserId Long userId,
		@PathVariable("cafeId") Long cafeId, @PathVariable("menuId") Long menuId) {
		menuService.deleteMenu(menuId);
	}
}
