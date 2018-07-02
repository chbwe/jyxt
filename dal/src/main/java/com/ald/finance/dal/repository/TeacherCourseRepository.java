package com.ald.finance.dal.repository;

import com.ald.finance.dal.entity.TeacherCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 *
 * @author liang3.zhang  2018-06-01 09:59:48
 */
public interface TeacherCourseRepository extends JpaRepository<TeacherCourse, Long>, JpaSpecificationExecutor<TeacherCourse> {

    List<TeacherCourse> findAllByUserId(Long userId);
}
