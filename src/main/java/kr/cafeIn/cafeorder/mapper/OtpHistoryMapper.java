package kr.cafeIn.cafeorder.mapper;

import java.time.LocalDateTime;
import kr.cafeIn.cafeorder.model.domain.OtpHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OtpHistoryMapper {

  void saveOtp(OtpHistory otpHistory);

  boolean existOtpKey(@Param("otpKey") String otpKey );

  LocalDateTime getExpireTime(@Param("otpKey") String otpKey);

}
