package com.ald.finance.dal.repository;

import com.ald.finance.dal.entity.TeacherCourseRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDate;
import java.util.List;

/**
 * @author liang3.zhang  2018-06-01 09:59:57
 */
public interface TeacherCourseRecordRepository extends JpaRepository<TeacherCourseRecord, Long>, JpaSpecificationExecutor<TeacherCourseRecord> {

    List<TeacherCourseRecord> findAllByCourseId(Long courseId);

    List<TeacherCourseRecord> findAllByCourseRecordDate(LocalDate localDate);

    List<TeacherCourseRecord> findAllByCourseRecordDateAndUserId(LocalDate localDate,Long userId);
}
