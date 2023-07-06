package kr.cafeIn.cafeorder.service;

import kr.cafeIn.cafeorder.exception.DuplicatedException;
import kr.cafeIn.cafeorder.mapper.CafeTableMapper;
import kr.cafeIn.cafeorder.model.domain.CafeTable;
import kr.cafeIn.cafeorder.model.dto.request.TableRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CafeTableService {

	private final CafeTableMapper cafeTableMapper;

	// 카페에 해당 테이블 번호가 존재하는지 확인.
	@Transactional(readOnly = true)
	public boolean isExistsCafeTableNumber(Integer tableNumber, Long cafeId) {
		return cafeTableMapper.isExistsCafeTableNumber(tableNumber, cafeId);
	}

	// 테이블 추가 생성하기.
	public void createTable(TableRequest tableRequest, Long cafeId) {
		if (isExistsCafeTableNumber(tableRequest.getTableNumber(), cafeId)) {
			throw new DuplicatedException("This table number already exists.");
		}

		CafeTable cafeTable = CafeTable.builder()
			.cafeId(cafeId)
			.tableNumber(tableRequest.getTableNumber())
			.capacity(tableRequest.getCapacity())
			.build();

		cafeTableMapper.insertCafeTable(cafeTable);
		cafeTableMapper.insertCafeTableLock(cafeTable.getId());
	}

	// 테이블 수정하기.
	public void updateTable(TableRequest tableRequest, Long tableId, Long cafeId) {

		CafeTable cafeTable = CafeTable.builder()
			.id(tableId)
			.cafeId(cafeId)
			.tableNumber(tableRequest.getTableNumber())
			.capacity(tableRequest.getTableNumber())
			.build();

		cafeTableMapper.updateCafeTable(cafeTable);
	}

}
