package com.ald.finance.dal.repository;

import com.ald.finance.dal.entity.StudentAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 *
 * @author liang3.zhang  2018-06-01 09:58:50
 */
public interface StudentAppointmentRepository extends JpaRepository<StudentAppointment, Long>, JpaSpecificationExecutor<StudentAppointment> {

    List<StudentAppointment> findAllByPayStatus(Integer status);
}
