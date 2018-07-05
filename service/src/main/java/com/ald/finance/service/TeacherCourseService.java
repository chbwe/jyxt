package com.ald.finance.service;

import com.ald.finance.common.utils.MyDateUtils;
import com.ald.finance.common.web.BusinessException;
import com.ald.finance.dal.entity.Packages;
import com.ald.finance.dal.entity.TeacherCourse;
import com.ald.finance.dal.entity.User;
import com.ald.finance.dal.repository.PackagesRepository;
import com.ald.finance.dal.repository.TeacherCourseRepository;
import com.ald.finance.dal.repository.UserRepository;
import com.ald.finance.service.dto.CourseDTO;
import com.ald.finance.service.utils.TeacherCourseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Created by liang3.zhang on 2018/6/1.
 */
@Service
public class TeacherCourseService {
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    TeacherCourseRepository teacherCourseRepository;
    
    @Autowired
    SysStudentCourseService sysStudentCourseService;
    
    @Autowired
    PackagesRepository packagesRepository;
    
    public List<CourseDTO> findAllByTeacherId(Long teacherId, Long packageId) {
        List<TeacherCourse> list = teacherCourseRepository.findAllByUserId(teacherId);
        Integer pacakgeTimes = null;
        if (packageId != null) {
            Packages packages = packagesRepository.findOne(packageId);
            pacakgeTimes = packages.getTimes();
//            list = list.stream().filter(s -> s.getTimes().equals(packages.getTimes())).collect(Collectors.toList());
        }
        return TeacherCourseUtils.listByTeacherId(list,pacakgeTimes);
    }

    
    /**
     * 我的课程列表
     *
     * @param teacherId
     * @return
     */
    public List<CourseDTO> listByTeacherId(Long teacherId) {
        List<TeacherCourse> list = teacherCourseRepository.findAllByUserId(teacherId);
        return TeacherCourseUtils.listByTeacherId(list);
    }
    
    /**
     * 更新某个老师的课程
     *
     * @return
     */
    public Boolean save(TeacherCourse teacherCourse, boolean isTeacher) {
        Long untils = teacherCourse.getStartTime().until(teacherCourse.getEndTime(), ChronoUnit.MINUTES);
        Integer times = untils.intValue();
        if (times != 60 && times != 90) {
            throw new BusinessException("上课时长只能为1小时、1.5小时");
        }
        if (teacherCourse.getId() != null) {
            TeacherCourse item1 = teacherCourseRepository.findOne(teacherCourse.getId());
            if (item1 == null) {
                throw new BusinessException("参数错误");
            }
            // 管理员可不进行时间长度限制
            if (isTeacher && !item1.getTimes().equals(times)) {
                throw new BusinessException("只能更改相同时间长度的时间范围");
            }
            teacherCourse.setCreateTime(item1.getCreateTime());
        } else {
            teacherCourse.setCreateTime(LocalDateTime.now());
        }
        teacherCourse.setUpdateTime(LocalDateTime.now());
        teacherCourse.setTimes(times);
        // 判断是否是否有冲突
        List<TeacherCourse> list = teacherCourseRepository.findAllByUserId(teacherCourse.getUserId());
        for (TeacherCourse item : list) {
            if (item.getId().equals(teacherCourse.getId()))
                continue;
            //
            if (!item.getWeekIndex().equals(teacherCourse.getWeekIndex()))
                continue;
            if (MyDateUtils.gte(teacherCourse.getStartTime(), item.getStartTime())
                && MyDateUtils.gte(item.getEndTime(), teacherCourse.getStartTime())) {
                throw new BusinessException("时间冲突");
            }
            if (MyDateUtils.gte(teacherCourse.getEndTime(), item.getStartTime())
                && MyDateUtils.gte(item.getEndTime(), teacherCourse.getEndTime())) {
                throw new BusinessException("时间冲突");
            }
        }
        teacherCourseRepository.save(teacherCourse);
        
        // 更改老师状态
        User user = userRepository.findOne(teacherCourse.getUserId());
        if (user != null && user.getStatus() == 0) {
            user.setStatus(1);
            user.setUpdateTime(LocalDateTime.now());
            userRepository.save(user);
        }
        
        // 同步更改课程
        sysStudentCourseService.changeCourse(teacherCourse);
        return true;
    }
}
