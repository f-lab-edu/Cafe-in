package kr.cafeIn.cafeorder.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import kr.cafeIn.cafeorder.exception.DuplicatedException;
import kr.cafeIn.cafeorder.mapper.MenuMapper;
import kr.cafeIn.cafeorder.model.domain.Menu;
import kr.cafeIn.cafeorder.model.dto.request.MenuSaveRequest;
import kr.cafeIn.cafeorder.model.dto.request.MenuUpdateRequest;
import kr.cafeIn.cafeorder.model.dto.response.MenuInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MenuServiceTest {

  @Mock
  MenuMapper menuMapper;

  @InjectMocks
  MenuService menuService;

  Menu menu;

  MenuSaveRequest menuSaveRequest;

  MenuInfoResponse menuInfoResponse;

  @BeforeEach
  public void saveMenu() {
    menu = Menu.builder()
        .id(1L)
        .name("Americano")
        .price(2000)
        .build();

    menuSaveRequest = MenuSaveRequest.builder()
        .name("Americano")
        .price(2000)
        .build();

    menuInfoResponse = MenuInfoResponse.builder()
        .name("Americano")
        .price(2000)
        .build();
  }

  @Test
  @DisplayName("메뉴저장에 성공합니다.")
  public void menuSaveTestWhenSuccess() {
    when(menuMapper.isExistsMenu(menuSaveRequest.getName())).thenReturn(false);
    menuService.saveMenu(menuSaveRequest);
    verify(menuMapper).insertMenu(any(Menu.class));
  }

  @Test
  @DisplayName("메뉴저장에 실패합니다. : 중복된 메뉴")
  public void menuSaveTestWhenFail() {
    when(menuMapper.isExistsMenu(menuSaveRequest.getName())).thenReturn(true);
    assertThrows(DuplicatedException.class, () -> menuService.saveMenu(menuSaveRequest));
    verify(menuMapper).isExistsMenu(menuSaveRequest.getName());
  }

  @Test
  @DisplayName("메뉴 조회에 성공합니다.")
  public void selectMenuByNameTestWhenSuccess() {
    when(menuMapper.selectMenuById(1L))
        .thenReturn(Optional.ofNullable(menuInfoResponse));
    menuService.getMenuInfo(1L);
    verify(menuMapper).selectMenuById(1L);
  }

  @Test
  @DisplayName("메뉴 정보 수정에 성공합니다.")
  public void updateMenuByIdWhenSuccess() {
    MenuUpdateRequest requestDto = MenuUpdateRequest.builder().build();
    menuService.updateMenuInfo(menu.getId(), requestDto);
    verify(menuMapper).updateMenuById(any(Menu.class));
  }

  @Test
  @DisplayName("메뉴삭제에 성공합니다.")
  public void deleteMenuWhenSuccess() {
    menuService.deleteMenu(menu.getId());
    verify(menuMapper).deleteMenu(menu.getId());
  }
}
