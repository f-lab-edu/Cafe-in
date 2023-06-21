package kr.cafeIn.cafeorder.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import kr.cafeIn.cafeorder.model.domain.CafeTable;
import kr.cafeIn.cafeorder.model.dto.response.CafeTableUseInfoResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CafeTableMapper {

	void insertCafeTables(List<CafeTable> cafeTables);

	void insertCafeTable(CafeTable cafeTable);

	void updateCafeTable(CafeTable cafeTable);

	void deleteCafeTable(Long cafeTableId);

	List<CafeTableUseInfoResponse> selectCafeTableUseInfo(@Param("cafeId") Long cafeId,
		@Param("searchTime") LocalDateTime searchTime);

	boolean isExistsCafeTableNumber(@Param("tableNumber") Integer tableNumber,
		@Param("cafeId") Long cafeId);

	Optional<Long> getCafeTableLockById(Long cafeTableId);

	void insertCafeTableLock(Long id);

	void insertCafeTableLocks(List<Long> ids);

}
