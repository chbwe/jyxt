package com.ald.finance.dal.repository;

import com.ald.finance.dal.entity.TeacherStopRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author liang3.zhang  2018-06-01 10:00:29
 */
public interface TeacherStopRecordRepository extends JpaRepository<TeacherStopRecord, Long>, JpaSpecificationExecutor<TeacherStopRecord> {

}
