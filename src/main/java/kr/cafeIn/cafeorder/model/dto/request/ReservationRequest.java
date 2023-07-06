package kr.cafeIn.cafeorder.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Reservation Request Dto.
 *
 * @since 1.0.0
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReservationRequest {

	@JsonProperty("start_time")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm",
		timezone = "Asia/Seoul")
	private LocalDateTime startTime;

	@JsonProperty("end_time")
	@JsonFormat(shape = Shape.STRING, pattern = "yyyy-MM-dd HH:mm",
		timezone = "Asia/Seoul")
	private LocalDateTime endTime;
}
