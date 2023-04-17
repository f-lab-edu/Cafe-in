package kr.cafeIn.cafeorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication
public class CafeorderApplication {

    public static void main(String[] args) {

        SpringApplication.run(CafeorderApplication.class, args);
    }

}
