package kr.cafeIn.cafeorder.service;


import java.util.List;
import kr.cafeIn.cafeorder.exception.DuplicatedException;
import kr.cafeIn.cafeorder.exception.NotFoundException;
import kr.cafeIn.cafeorder.mapper.MenuMapper;
import kr.cafeIn.cafeorder.model.domain.Menu;
import kr.cafeIn.cafeorder.model.dto.request.MenuRequest;
import kr.cafeIn.cafeorder.model.dto.response.MenuInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {
	private final MenuMapper menuMapper;


	@Transactional(readOnly = true)
	public boolean isExistsMenu(String name, Long cafeId) {
		return menuMapper.isExistsMenu(name, cafeId);
	}

	/**
	 * 메뉴 등록.
	 *
	 * @param menuRequest 메뉴 등록 DTO
	 * @since 1.0.0
	 */

	public void createMenu(MenuRequest menuRequest, Long cafeId) {
		if (isExistsMenu(menuRequest.getName(), cafeId)) {
			throw new DuplicatedException("This menu already exists.");
		}

		Menu menu = Menu.builder()
			.cafeId(cafeId)
			.name(menuRequest.getName())
			.price(menuRequest.getPrice())
			.build();
		menuMapper.createMenu(menu);
	}

	/**
	 * id에 해당하는 메뉴 정보 조회.
	 *
	 * @param menuId 메뉴 id.
	 * @since 1.0.0
	 */

	public MenuInfoResponse getMenuInfo(Long menuId) {
		//menuId = Long.valueOf(1);
		return menuMapper.selectMenuById(menuId)
			.orElseThrow(() -> new NotFoundException("Select not found menu"));
	}

	/**
	 * 메뉴 정보 전체 조회.
	 * @since 1.0.0
	 */

	public List<Menu> getMenuAll() {
		List<Menu> menuList = menuMapper.selectMenuAll();

		return menuList;
	}

	/**
	 * id에 해당하는 메뉴 정보 수정. (name, price).
	 *
	 * @param menuId 메뉴 ID.
	 * @param menuRequest 메뉴수정 DTO.
	 * @since 1.0.0
	 */

	public void updateMenu(MenuRequest menuRequest, Long menuId, Long cafeId) {
		Menu menu = Menu.builder()
			.id(menuId)
			.cafeId(cafeId)
			.name(menuRequest.getName())
			.price(menuRequest.getPrice())
			.build();

		menuMapper.updateMenu(menu);
	}

	/**
	 * id에 해당하는 메뉴 삭제.
	 * @param menuId 메뉴 ID.
	 * @since 1.0.0
	 */

	public void deleteMenu(Long menuId) {
		menuMapper.deleteMenu(menuId);
	}

}
