package com.ald.finance.dal.repository;

import com.ald.finance.dal.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;

/**
 *
 * @author liang3.zhang  2018-06-01 09:58:30
 */
public interface HolidayRepository extends JpaRepository<Holiday, Long>, JpaSpecificationExecutor<Holiday> {

    Holiday findOneByHolidayDay(LocalDate holidayDay);
}
