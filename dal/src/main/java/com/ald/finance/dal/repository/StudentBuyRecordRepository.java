package com.ald.finance.dal.repository;

import com.ald.finance.dal.entity.StudentBuyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author liang3.zhang  2018-06-01 09:59:01
 */
public interface StudentBuyRecordRepository extends JpaRepository<StudentBuyRecord, Long>, JpaSpecificationExecutor<StudentBuyRecord> {

    List<StudentBuyRecord> findAllByUserId(Long userId);

    List<StudentBuyRecord> findAllByStatus(Integer status);
}
