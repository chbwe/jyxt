package com.ald.finance.dal.repository;

import com.ald.finance.dal.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 *
 * @author liang3.zhang 2018-06-01 10:01:40
 */
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    /**
     * 根据用户手机号或者昵称查询
     * @param userMobile
     * @param userNickname
     * @return
     */
    User findOneByUserMobileOrUserNickname(String userMobile,String userNickname);

    /**
     * 根据手机号查询
     * @param userMobile
     * @return
     */
    User findOneByUserMobile(String userMobile);

    /**
     * 根据用户昵称查询
     * @param userNickname
     * @return
     */
    User findOneByUserNickname(String userNickname);
}
