package com.ald.finance.dal.repository;

import com.ald.finance.dal.entity.Upload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author liang3.zhang  2018-06-01 10:01:32
 */
public interface UploadRepository extends JpaRepository<Upload, String>, JpaSpecificationExecutor<Upload> {

}
