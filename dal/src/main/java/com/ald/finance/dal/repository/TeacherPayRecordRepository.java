package com.ald.finance.dal.repository;

import com.ald.finance.dal.entity.TeacherPayRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author liang3.zhang  2018-06-01 10:00:17
 */
public interface TeacherPayRecordRepository extends JpaRepository<TeacherPayRecord, Long>, JpaSpecificationExecutor<TeacherPayRecord> {
    TeacherPayRecord findOneByUserIdAndYearAndMonth(Long teacherId,int year,int month);
}
