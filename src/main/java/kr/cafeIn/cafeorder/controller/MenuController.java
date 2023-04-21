package kr.cafeIn.cafeorder.controller;

import java.util.List;
import javax.validation.Valid;
import kr.cafeIn.cafeorder.model.domain.Menu;
import kr.cafeIn.cafeorder.model.dto.request.MenuSaveRequest;
import kr.cafeIn.cafeorder.model.dto.request.MenuUpdateRequest;
import kr.cafeIn.cafeorder.model.dto.response.MenuInfoResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/cafes/menu")
@RequiredArgsConstructor
@RestController
public class MenuController {

	private final MenuService menuService;

	/**
	 * 메뉴 등록.
	 * @param menuSaveRequest 메뉴등록 입력 정보
	 * @since 1.0.0
	 */

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void saveMenu(@Valid @RequestBody MenuSaveRequest menuSaveRequest) {
		menuService.saveMenu(menuSaveRequest);
	}

	/**
	 * 메뉴 조회.
	 * @param menuId 메뉴 ID.
	 * @since 1.0.0
	 */

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public MenuInfoResponse getMenuInfo(@RequestParam("id") Long menuId) {
		return menuService.getMenuInfo(menuId);
	}

	/**
	 * 메뉴 전체 조회.
	 * @since 1.0.0
	 */

	@GetMapping("/all")
	@ResponseStatus(HttpStatus.CREATED)
	public List<Menu> getMenuAll() {
		return menuService.getMenuAll();
	}

	/**
	 * 메뉴 정보 수정.
	 * @param menuId 메뉴 ID.
	 * @since 1.0.0
	 */

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public void updateMenuInfo(@PathVariable("id") Long menuId, @RequestBody MenuUpdateRequest menuUpdateRequest) {
		menuService.updateMenuInfo(menuId, menuUpdateRequest);
	}

	/**
	 * 메뉴 삭제.
	 * @param menuId 메뉴 ID.
	 * @since 1.0.0
	 */

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public void deleteMenu(@PathVariable("id") Long menuId) {
		menuService.deleteMenu(menuId);
	}

}
