package kr.cafeIn.cafeorder.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import kr.cafeIn.cafeorder.exception.DuplicatedException;
import kr.cafeIn.cafeorder.exception.NotFoundException;
import kr.cafeIn.cafeorder.mapper.CafeTableMapper;
import kr.cafeIn.cafeorder.mapper.OrderMapper;
import kr.cafeIn.cafeorder.model.domain.Order;
import kr.cafeIn.cafeorder.model.dto.request.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

	private final OrderMapper orderMapper;

	private final CafeTableMapper cafeTableMapper;

	/**
	 * 주문(예약) 하기.
	 * 1. 시작 시간이 현재 시간보다 앞에 있는 경우.
	 * 2. 시작 시간이 완료 시간보다 뒤에 있는 경우.
	 * 3. 총 이용 시간이 10분 이내일 경우 예외 발생.
	 *
	 * @param reservationRequest    예약 DTO
	 * @param userId                유저 ID
	 * @param cafeTableId           테이블 ID
	 * @since 1.0.0
	 */

	public void createOrder(ReservationRequest reservationRequest, Long userId, Long cafeTableId) {
		LocalDateTime startTime = reservationRequest.getStartTime();
		LocalDateTime endTime = reservationRequest.getEndTime();

		if (startTime.isBefore(LocalDateTime.now()) || startTime.isAfter(endTime)
			|| ChronoUnit.MINUTES.between(startTime, endTime) < 10) {
			throw new IllegalArgumentException("Please check your reservation time again.");
		}

		cafeTableMapper.getCafeTableLockById(cafeTableId)
			.orElseThrow(() -> new NotFoundException("Select not found cafeTable_lock"));

		if (orderMapper.isExistsOrderByCafeTableIdAndUseTime(cafeTableId, startTime, endTime)) {
			throw new DuplicatedException("This Reservation Time already exists.");
		}

		Order order = Order.builder()
			.userId(userId)
			.tableId(cafeTableId)
			.startTime(startTime)
			.endTime(endTime)
			.build();

		orderMapper.insertOrder(order);
	}

	/**
	 * 주문(예약) 취소하기.
	 * 주문 시작 시간이 10분 전에 취소 가능. 10분 이내일 경우 예외 발생.
	 *
	 */
	public void cancelOrder(Long userId, Long orderId) {
		Order order = orderMapper.selectOrderByIdAndUserId(userId,
			orderId).orElseThrow(() -> new NotFoundException("Select not found order"));

		if (order.getStartTime().minusMinutes(10).isBefore(LocalDateTime.now())) {
			throw new IllegalArgumentException("Order can not be canceled 10 minutes");
		}

		orderMapper.deleteOrder(orderId);
	}
}
