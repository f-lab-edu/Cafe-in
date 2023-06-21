package kr.cafeIn.cafeorder.mapper;

import java.time.LocalDateTime;
import java.util.Optional;
import kr.cafeIn.cafeorder.model.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {

	void insertOrder(Order order);

	boolean isExistsNowOrderByCafeTableId(Long cafeTableId);

	boolean isExistsOrderByCafeTableIdAndUseTime(@Param("roomId") Long roomId,
		@Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

	Optional<Order> selectOrderByIdAndUserId(@Param("userId") Long userId,
		@Param("orderId") Long orderId);

	void deleteOrder(Long id);
}
