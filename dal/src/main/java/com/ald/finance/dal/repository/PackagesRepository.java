package com.ald.finance.dal.repository;

import com.ald.finance.dal.entity.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author liang3.zhang  2018-06-01 09:58:39
 */
public interface PackagesRepository extends JpaRepository<Packages, Long>, JpaSpecificationExecutor<Packages> {

}
