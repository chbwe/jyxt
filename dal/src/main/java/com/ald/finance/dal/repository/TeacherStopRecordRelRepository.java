package com.ald.finance.dal.repository;

import com.ald.finance.dal.entity.TeacherStopRecordRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author liang3.zhang  2018-06-01 10:00:39
 */
public interface TeacherStopRecordRelRepository extends JpaRepository<TeacherStopRecordRel, Long>, JpaSpecificationExecutor<TeacherStopRecordRel> {

}
