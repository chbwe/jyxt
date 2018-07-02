package com.ald.finance.dal.repository;

import com.ald.finance.dal.entity.StudentCourseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author liang3.zhang  2018-06-01 09:59:10
 */
public interface StudentCourseRecordRepository extends JpaRepository<StudentCourseRecord, Long>, JpaSpecificationExecutor<StudentCourseRecord> {

    List<StudentCourseRecord> findAllByBuyRecordId(Long buyRecordId);

    List<StudentCourseRecord> findAllByUserId(Long userId);

    StudentCourseRecord findOneByCourseDateAndUserIdAndCourseIndex(LocalDate courseDate,Long userId,Integer courseIndex);
}
