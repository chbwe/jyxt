package com.ald.finance.dal.repository;

import com.ald.finance.dal.entity.StudentLeaveRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 *
 * @author liang3.zhang  2018-06-01 09:59:19
 */
public interface StudentLeaveRecordRepository extends JpaRepository<StudentLeaveRecord, Long>, JpaSpecificationExecutor<StudentLeaveRecord> {

    List<StudentLeaveRecord> findAllByUserId(Long userId);
}
