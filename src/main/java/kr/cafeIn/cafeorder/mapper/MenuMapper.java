package kr.cafeIn.cafeorder.mapper;

import java.util.List;
import java.util.Optional;
import kr.cafeIn.cafeorder.model.domain.Menu;
import kr.cafeIn.cafeorder.model.dto.response.MenuInfoResponse;

public interface MenuMapper {

  void insertMenu(Menu menu);

  boolean isExistsMenu(String name);

  Optional<MenuInfoResponse> selectMenuById(Long menuId);

  List<Menu> selectMenuAll();

  void updateMenuById(Menu menu);

  void deleteMenu(Long id);

}
