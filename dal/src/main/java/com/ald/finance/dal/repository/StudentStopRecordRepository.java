package com.ald.finance.dal.repository;

import com.ald.finance.dal.entity.StudentStopRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author liang3.zhang  2018-06-01 09:59:27
 */
public interface StudentStopRecordRepository extends JpaRepository<StudentStopRecord, Long>, JpaSpecificationExecutor<StudentStopRecord> {

}
