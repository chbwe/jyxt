package com.ald.finance.service;

import com.ald.finance.common.constants.JYXTConstant;
import com.ald.finance.common.enums.UserRoleEnum;
import com.ald.finance.common.web.BusinessException;
import com.ald.finance.dal.entity.User;
import com.ald.finance.dal.repository.UserRepository;
import com.ald.finance.service.query.UserQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liang3.zhang on 2018/6/1.
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /**
     * 查询学生列表
     *
     * @param name
     * @param pageable
     * @return
     */
    public Page<User> findStudentList(String name, Pageable pageable) {
        return findAll(new UserQuery().name(name).userRole(UserRoleEnum.Student.getRoleId()), pageable);
    }

    // 查询老师列表
    public Page<User> findTeacherList(UserQuery query, Pageable pageable) {
        query.setUserRole(UserRoleEnum.Teacher.getRoleId());
        return findAll(query, pageable);
    }

    /**
     * 查询单个用户
     *
     * @param id
     * @return
     */
    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    /**
     * 更改密码
     *
     * @param userId
     * @param password
     * @param newPassword
     */
    public void changePwd(Long userId, String password, String newPassword) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        if (!password.equals(user.getUserPwd())) {
            throw new BusinessException("旧密码不对");
        }
        user.setUserPwd(newPassword);
        userRepository.save(user);
    }

    /**
     * 重置密码
     *
     * @param userId
     */
    public void resetPassword(Long userId) {
        User user = userRepository.findOne(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        user.setUserPwd(JYXTConstant.DEFAULT_PASSWORD);
        userRepository.save(user);
    }

    /**
     * 登陆
     *
     * @param username
     * @param password
     * @return
     */
    public User login(String username, String password) {
        User user = findOneByLogin(username);
        if (user == null) {
            throw new BusinessException("用户名不存在");
        }
        if (!user.getUserPwd().equals(password)) {
            throw new BusinessException("密码错误");
        }
        return user;
    }

    /**
     * 新增老师
     */
    public Long addTeacher(User user) {
        User user1 = userRepository.findOneByUserMobile(user.getUserMobile());
        if (user1 != null) {
            throw new BusinessException("用户手机号已被占用");
        }
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setUserPwd(JYXTConstant.DEFAULT_PASSWORD);// 设置默认密码为123456
        user = userRepository.save(user);
        return user.getId();
    }

    /**
     * 更新老师（不更新老师
     *
     * @param user
     * @return
     */
    public boolean updateTeacher(User user) {
        User user1 = userRepository.findOne(user.getId());
        user1.setUserContent(user.getUserContent());
        user1.setUserImg(user.getUserImg());
        user1.setUserSchool(user.getUserSchool());
        user1.setUpdateTime(LocalDateTime.now());
        user1.setUserNickname(user.getUserNickname());
        user1.setUserPrice2(user.getUserPrice2());
        user1.setUserPrice(user.getUserPrice());
        userRepository.save(user1);
        return true;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public Map<Long, User> loadUserMap(List<Long> ids) {
        List<User> users = userRepository.findAll(ids);
        Map<Long, User> map = new ConcurrentHashMap<>();
        for (User user : users) {
            map.put(user.getId(), user);
        }
        return map;
    }

    public User findOneByUserMobile(String userMobile) {
        return userRepository.findOneByUserMobile(userMobile);
    }

    public User findOneByUserMobileOrUserNickname(String userMobile, String userNickName) {
        return userRepository.findOneByUserMobileOrUserNickname(userMobile, userNickName);
    }

    // 根据用户手机号（昵称）查询
    private User findOneByLogin(String login) {
        return userRepository.findOneByUserMobileOrUserNickname(login, login);
    }

    // 查询用户列表
    public Page<User> findAll(UserQuery query, Pageable pageable) {
        Specification<User> specification = getSpecification(query);
        return userRepository.findAll(specification, pageable);
    }

    private Specification<User> getSpecification(final UserQuery query) {
        return new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                if (query.getUserRole() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("userRole"), query.getUserRole()));
                }
                if (query.getUserId() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("id"), query.getUserId()));
                }
                if (query.getStatus() != null) {
                    predicateList.add(criteriaBuilder.equal(root.get("status"), query.getStatus()));
                }
                if (!StringUtils.isEmpty(query.getName())) {
                    predicateList.add(criteriaBuilder.like(root.get("userNickname"), '%' + query.getName() + '%'));
                }
                Predicate[] predicates = new Predicate[predicateList.size()];
                Order order = criteriaBuilder.desc(root.get("updateTime").as(Date.class));
                return criteriaQuery.where(predicateList.toArray(predicates)).orderBy(order).getRestriction();
            }
        };
    }
}
