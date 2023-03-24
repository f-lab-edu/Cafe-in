package kr.cafeIn.cafeorder;




import kr.cafeIn.cafeorder.repository.UserRepository;

import kr.cafeIn.cafeorder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {


   private final DataSource dataSource;


    public SpringConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }





}

