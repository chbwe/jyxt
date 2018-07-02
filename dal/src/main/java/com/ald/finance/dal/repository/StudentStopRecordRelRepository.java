package com.ald.finance.dal.repository;

import com.ald.finance.dal.entity.StudentStopRecordRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author liang3.zhang  2018-06-01 09:59:38
 */
public interface StudentStopRecordRelRepository extends JpaRepository<StudentStopRecordRel, Long>, JpaSpecificationExecutor<StudentStopRecordRel> {

}
