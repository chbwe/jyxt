package com.ald.finance.dal.repository;

import com.ald.finance.dal.entity.TeacherCourseRecordRel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 *
 * @author liang3.zhang  2018-06-01 14:08:36
 */
public interface TeacherCourseRecordRelRepository extends JpaRepository<TeacherCourseRecordRel, Integer>, JpaSpecificationExecutor<TeacherCourseRecordRel> {
    TeacherCourseRecordRel findOneByStudentCourseRecordId(Long studentCourseRecordId);

    List<TeacherCourseRecordRel> findAllByStudentCourseRecordIdIn(List<Long> studentCourseRecordIds);

    List<TeacherCourseRecordRel> findAllByTeacherCourseRecordIdIn(List<Long> teacherCourseRecordId);
}
