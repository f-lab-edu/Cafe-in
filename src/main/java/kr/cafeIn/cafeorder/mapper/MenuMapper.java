package kr.cafeIn.cafeorder.mapper;

import java.util.List;
import java.util.Optional;
import kr.cafeIn.cafeorder.model.domain.Menu;
import kr.cafeIn.cafeorder.model.dto.response.MenuInfoResponse;
import org.apache.ibatis.annotations.Param;

public interface MenuMapper {

	void createMenu(Menu menu);

	boolean isExistsMenu(@Param("name") String name,
		@Param("cafeId") Long cafeId);

	Optional<MenuInfoResponse> selectMenuById(Long menuId);

	List<Menu> selectMenuAll();

	void updateMenu(Menu menu);

	void deleteMenu(Long id);

}
