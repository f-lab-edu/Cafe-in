package kr.cafeIn.cafeorder.service;

import kr.cafeIn.cafeorder.model.domain.User;
import kr.cafeIn.cafeorder.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service //스프링 컨테이너에 등록을 한다 .
public class UserService {
    private final UserRepository userRepository;


    @Autowired
    public UserService(@Qualifier("myBatisUserRepository") UserRepository userRepository) {

        this.userRepository = userRepository;
    }




    /**
     * 회원가입
     *
     * @param
     * @return
     */

    public String join(User user) {


        //중복회원 검증
        validateDuplicateUser(user);
        userRepository.save(user);//통과시 저장
        return user.getEmail();//회원가입 하면 id만 반환해 준다


    }

    private void validateDuplicateUser(User users) {//같은 이름이 있는 중복 회원 x
        userRepository.findByEmail(users.getEmail())
                .ifPresent(m -> {// user에 이미 값이 있으면
                    throw new IllegalStateException("이미존재하는 회원입니다");
                });
    }

    public Optional<User> findOne(String email){

        return  userRepository.findByEmail(email);
    }


}
