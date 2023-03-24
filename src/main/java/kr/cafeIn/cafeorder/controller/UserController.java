package kr.cafeIn.cafeorder.controller;

import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")//조회 할때 주로 사용
    public String createForm() {
        return "user";
    }

    @PostMapping("/users")//form 데이터 보낼때 ( 데이터 등록시 사용) url은 똑같지만 !

    public String create(User user) {
        User user1 = new User();
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        user.setNickname(user.getNickname());
        user.setGrade(user.getGrade());
        // create_at, grade,update_at도 넣어야 하는가?
        userService.join(user);
        return  "redirect:/";

    }


}
