package com.tulio.deltafitgym.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tulio.deltafitgym.model.Payment;
import com.tulio.deltafitgym.model.dto.MonthlyEarningsChartDTO;
import com.tulio.deltafitgym.model.enums.EnumPaymentStatus;

public interface IPaymentRepository extends JpaRepository<Payment, Long>{

	@Query("SELECT DISTINCT EXTRACT(year from dateTimeRecord) "
			+ " FROM Payment WHERE status = :completedStatus")
	public List<String> getYearsList(EnumPaymentStatus completedStatus);
	
	@Query("SELECT new com.tulio.deltafitgym.model.dto.MonthlyEarningsChartDTO( month(dateTimeRecord), SUM(value) )"
			+ " FROM Payment "
			+ " WHERE year(dateTimeRecord) = :yearFilter"
			+ " AND status = :completedStatus"
			+ " GROUP BY month(dateTimeRecord)"
			+ "	ORDER BY month(dateTimeRecord)")
	public List<MonthlyEarningsChartDTO> getMonthlyEarningsChartData(Integer yearFilter, EnumPaymentStatus completedStatus);
}
