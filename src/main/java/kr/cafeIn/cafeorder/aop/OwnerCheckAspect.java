package kr.cafeIn.cafeorder.aop;

import kr.cafeIn.cafeorder.exception.AccessDeniedException;
import kr.cafeIn.cafeorder.exception.NotFoundException;
import kr.cafeIn.cafeorder.mapper.CafeMapper;
import kr.cafeIn.cafeorder.model.domain.Cafe;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class OwnerCheckAspect {

	private final CafeMapper cafeMapper;

	/**
	 * {@link kr.cafeIn.cafeorder.annotation.OwnerCheck} 가 붙어있는 메소드에 관리자 검증 AOP 적용.
	 *
	 * @since 1.0.0
	 */

	@Before("@annotation(kr.cafeIn.cafeorder.annotation.OwnerCheck) && args(userId, cafeId, ..)")
	public void ownerCheck(Long userId, Long cafeId) {
		Cafe cafe = cafeMapper.selectCafeById(cafeId)
			.orElseThrow(() -> new NotFoundException("Select not found cafe."));

		if (!cafe.getUserId().equals(userId)) {
			throw new AccessDeniedException("The service cannot be accessed.");
		}
	}

}
