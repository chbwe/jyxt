package com.ald.finance.service.utils;

import com.ald.finance.common.web.BusinessException;
import com.ald.finance.dal.entity.TeacherCourse;
import com.ald.finance.service.dto.CourseDTO;
import com.ald.finance.service.dto.StudentCourseRecordDTO;
import com.ald.finance.service.dto.TeacherCourseRecordDTO;
import org.springframework.util.CollectionUtils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by liang3.zhang on 2018/5/6.
 */
public class TeacherCourseUtils {

    static final String[] WEEK = new String[]{"", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};

    static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * 根据传入的时间查询周一
     *
     * @param
     * @return
     */
    public static LocalDate getFirstWeekWithNow() {
        LocalDate local = LocalDate.now();
        DayOfWeek dayOfWeek = local.getDayOfWeek();
        return local.minusDays(dayOfWeek.getValue() - 1);
    }

    public static LocalDate getFirstWeekWithStartDate(String start) {
        LocalDate local = LocalDate.parse(start);
        DayOfWeek dayOfWeek = local.getDayOfWeek();
        return local.minusDays(dayOfWeek.getValue() - 1);
    }

    public static LocalDate getEndDate(LocalDate start) {
        ;
        return start.plusDays(7);
    }

    /**
     * 请假时间验证
     *
     * @param
     * @return
     */
    public static void valid(LocalDate start, LocalDate end) {
        if (end.compareTo(start) < 0) {
            throw new BusinessException("开始时间不能小于结算时间");
        }
        if (start.isBefore(LocalDate.now())) {
            throw new BusinessException("只能请假今天以后的日期");
        }
    }

    public static List<CourseDTO> recordByStudent(List<StudentCourseRecordDTO> list, LocalDate start) {
        List<CourseDTO> temp = new ArrayList<>();
        Map<Integer, List<StudentCourseRecordDTO>> map1 = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            map1 = list.stream()
                    .collect(Collectors.groupingBy(StudentCourseRecordDTO::getWeekIndex, Collectors.toList()));
        }

        for (int i = 1; i <= 7; i++) {
            CourseDTO dto = new CourseDTO();
            dto.setWeekIndex(i);
            dto.setWeekDay(start.plusDays(i - 1).toString());
            dto.setWeek(WEEK[i]);
            Map<Integer, StudentCourseRecordDTO> map = new HashMap<>();
            if (map1.containsKey(i)) {
                for (StudentCourseRecordDTO course : map1.get(i)) {
                    map.put(course.getCourseIndex(), course);
                }
            }
            dto.setTime1(getCourseIndexTimeWithStudent(map, 1));
            dto.setKey1(getCourseIndexKeyWithStudent(map, 1, i));

            dto.setTime2(getCourseIndexTimeWithStudent(map, 2));
            dto.setKey2(getCourseIndexKeyWithStudent(map, 2, i));

            dto.setTime3(getCourseIndexTimeWithStudent(map, 3));
            dto.setKey3(getCourseIndexKeyWithStudent(map, 3, i));

            dto.setTime4(getCourseIndexTimeWithStudent(map, 4));
            dto.setKey4(getCourseIndexKeyWithStudent(map, 4, i));

            dto.setTime5(getCourseIndexTimeWithStudent(map, 5));
            dto.setKey5(getCourseIndexKeyWithStudent(map, 5, i));

            dto.setTime6(getCourseIndexTimeWithStudent(map, 6));
            dto.setKey6(getCourseIndexKeyWithStudent(map, 6, i));

            dto.setTime7(getCourseIndexTimeWithStudent(map, 7));
            dto.setKey7(getCourseIndexKeyWithStudent(map, 7, i));

            dto.setTime8(getCourseIndexTimeWithStudent(map, 8));
            dto.setKey8(getCourseIndexKeyWithStudent(map, 8, i));
            temp.add(dto);
        }
        return temp;
    }


    //老师上课记录表
    public static List<CourseDTO> recordByTeacherId(List<TeacherCourseRecordDTO> list, LocalDate start) {
        List<CourseDTO> temp = new ArrayList<>();
        Map<Integer, List<TeacherCourseRecordDTO>> map1 = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            map1 = list.stream()
                    .collect(Collectors.groupingBy(TeacherCourseRecordDTO::getWeekIndex, Collectors.toList()));
        }

        for (int i = 1; i <= 7; i++) {
            CourseDTO dto = new CourseDTO();
            dto.setWeekIndex(i);
            dto.setWeekDay(start.plusDays(i - 1).toString());
            dto.setWeek(WEEK[i]);
            Map<Integer, TeacherCourseRecordDTO> map = new HashMap<>();
            if (map1.containsKey(i)) {
                for (TeacherCourseRecordDTO course : map1.get(i)) {
                    map.put(course.getCourseRecordIndex(), course);
                }
            }
            dto.setTime1(getCourseIndexTime(map, 1));
            dto.setKey1(getCourseIndexKey(map, 1, i));

            dto.setTime2(getCourseIndexTime(map, 2));
            dto.setKey2(getCourseIndexKey(map, 2, i));

            dto.setTime3(getCourseIndexTime(map, 3));
            dto.setKey3(getCourseIndexKey(map, 3, i));

            dto.setTime4(getCourseIndexTime(map, 4));
            dto.setKey4(getCourseIndexKey(map, 4, i));

            dto.setTime5(getCourseIndexTime(map, 5));
            dto.setKey5(getCourseIndexKey(map, 5, i));

            dto.setTime6(getCourseIndexTime(map, 6));
            dto.setKey6(getCourseIndexKey(map, 6, i));

            dto.setTime7(getCourseIndexTime(map, 7));
            dto.setKey7(getCourseIndexKey(map, 7, i));

            dto.setTime8(getCourseIndexTime(map, 8));
            dto.setKey8(getCourseIndexKey(map, 8, i));
            temp.add(dto);
        }
        return temp;
    }



    public static List<CourseDTO> listByTeacherId(List<TeacherCourse> list,Integer packageTime) {
        List<CourseDTO> temp = new ArrayList<>();
        Map<Integer, List<TeacherCourse>> map1 = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            map1 = list.stream()
                    .collect(Collectors.groupingBy(TeacherCourse::getWeekIndex, Collectors.toList()));
        }
        for (int i = 1; i <= 7; i++) {
            CourseDTO dto = new CourseDTO();
            dto.setWeekIndex(i);
            dto.setWeekDay(WEEK[i]);
            dto.setWeek(WEEK[i]);
            Map<Integer, TeacherCourse> map = new HashMap<>();
            if (map1.containsKey(i)) {
                for (TeacherCourse course : map1.get(i)) {
                    map.put(course.getCourseIndex(), course);
                }
            }
            dto.setTime1(getIndexTime(map, 1));
            dto.setKey1(getIndexKey(map, 1, i));
            if(packageTime == null){
                dto.setShow1(true);
            }
            else {
                dto.setShow1(getPackage(map, 1).equals(packageTime));
            }

            dto.setTime2(getIndexTime(map, 2));
            dto.setKey2(getIndexKey(map, 2, i));
            if(packageTime == null){
                dto.setShow2(true);
            }
            else {
                dto.setShow2(getPackage(map, 2).equals(packageTime));
            }

            dto.setTime3(getIndexTime(map, 3));
            dto.setKey3(getIndexKey(map, 3, i));
            if(packageTime == null){
                dto.setShow3(true);
            }
            else {
                dto.setShow3(getPackage(map, 3).equals(packageTime));
            }


            dto.setTime4(getIndexTime(map, 4));
            dto.setKey4(getIndexKey(map, 4, i));
            if(packageTime == null){
                dto.setShow4(true);
            }
            else {
                dto.setShow4(getPackage(map, 4).equals(packageTime));
            }

            dto.setTime5(getIndexTime(map, 5));
            dto.setKey5(getIndexKey(map, 5, i));
            if(packageTime == null){
                dto.setShow5(true);
            }
            else {
                dto.setShow5(getPackage(map, 5).equals(packageTime));
            }


            dto.setTime6(getIndexTime(map, 6));
            dto.setKey6(getIndexKey(map, 6, i));
            if(packageTime == null){
                dto.setShow6(true);
            }
            else {
                dto.setShow6(getPackage(map, 6).equals(packageTime));
            }


            dto.setTime7(getIndexTime(map, 7));
            dto.setKey7(getIndexKey(map, 7, i));
            if(packageTime == null){
                dto.setShow7(true);
            }
            else {
                dto.setShow7(getPackage(map, 7).equals(packageTime));
            }


            dto.setTime8(getIndexTime(map, 8));
            dto.setKey8(getIndexKey(map, 8, i));
            if(packageTime == null){
                dto.setShow8(true);
            }
            else {
                dto.setShow8(getPackage(map, 8).equals(packageTime));
            }

            temp.add(dto);
        }
        return temp;
    }



    //课程表
    public static List<CourseDTO> listByTeacherId(List<TeacherCourse> list) {
        List<CourseDTO> temp = new ArrayList<>();
        Map<Integer, List<TeacherCourse>> map1 = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)) {
            map1 = list.stream()
                    .collect(Collectors.groupingBy(TeacherCourse::getWeekIndex, Collectors.toList()));
        }
        for (int i = 1; i <= 7; i++) {
            CourseDTO dto = new CourseDTO();
            dto.setWeekIndex(i);
            dto.setWeekDay(WEEK[i]);
            dto.setWeek(WEEK[i]);
            Map<Integer, TeacherCourse> map = new HashMap<>();
            if (map1.containsKey(i)) {
                for (TeacherCourse course : map1.get(i)) {
                    map.put(course.getCourseIndex(), course);
                }
            }
            dto.setTime1(getIndexTime(map, 1));
            dto.setKey1(getIndexKey(map, 1, i));

            dto.setTime2(getIndexTime(map, 2));
            dto.setKey2(getIndexKey(map, 2, i));

            dto.setTime3(getIndexTime(map, 3));
            dto.setKey3(getIndexKey(map, 3, i));

            dto.setTime4(getIndexTime(map, 4));
            dto.setKey4(getIndexKey(map, 4, i));

            dto.setTime5(getIndexTime(map, 5));
            dto.setKey5(getIndexKey(map, 5, i));

            dto.setTime6(getIndexTime(map, 6));
            dto.setKey6(getIndexKey(map, 6, i));

            dto.setTime7(getIndexTime(map, 7));
            dto.setKey7(getIndexKey(map, 7, i));

            dto.setTime8(getIndexTime(map, 8));
            dto.setKey8(getIndexKey(map, 8, i));
            temp.add(dto);
        }
        return temp;
    }

    private static String getCourseIndexKey(Map<Integer, TeacherCourseRecordDTO> map, int i, int weekIndex) {
        if (map.containsKey(i)) {
            TeacherCourseRecordDTO obj = map.get(i);
            switch (obj.getCourseRecordStatus()) {
                case 0: {
                    return "未上课(" + obj.getCurrent() + "人)";
                }
                case 1: {
                    return "已上课(" + obj.getCurrent() + "人)";
                }
                case -1: {
                    return "已请假";
                }
                case -2: {
                    return "已停课";
                }
                case -9: {
                    return "节假日";
                }
            }
        }
        return "无学生上课";
    }

    private static String getCourseIndexTime(Map<Integer, TeacherCourseRecordDTO> map, int i) {
        if (map.containsKey(i)) {
            TeacherCourseRecordDTO obj = map.get(i);
            LocalTime time1 = obj.getCourseRecordStartTime();
            LocalTime time2 = obj.getCourseRecordEndTime();
            return time1.format(dateTimeFormatter) + "-" + time2.format(dateTimeFormatter);
        }
        return "无";
    }

    private static String getCourseIndexTimeWithStudent(Map<Integer, StudentCourseRecordDTO> map, int i) {
        if (map.containsKey(i)) {
            StudentCourseRecordDTO obj = map.get(i);
            return obj.getId().toString();
        }
        return "无";
    }

    private static String getCourseIndexKeyWithStudent(Map<Integer, StudentCourseRecordDTO> map, int i, int weekIndex) {
        if (map.containsKey(i)) {
            StudentCourseRecordDTO obj = map.get(i);
            switch (obj.getStatus()) {
                case 0: {
                    return "未上课";
                }
                case 1: {
                    return "已上课";
                }
                case -1: {
                    return "已请假";
                }
                case -2: {
                    return "已停课";
                }
                case -9: {
                    return "节假日";
                }
            }
        }
        return "无";
    }


    private static String getIndexTime(Map<Integer, TeacherCourse> map, int i) {
        if (map.containsKey(i)) {
            return toString(map.get(i));
        }
        return "无";
    }
    private static Integer getPackage(Map<Integer, TeacherCourse> map, int i){
        if (map.containsKey(i)) {
            return map.get(i).getTimes();
        }
        return 0;
    }

    private static String getIndexKey(Map<Integer, TeacherCourse> map, int i, int weekIndex) {
        if (map.containsKey(i)) {
            TeacherCourse course = map.get(i);
            return weekIndex + "_" + i + "_" + course.getId();
        }
        return weekIndex + "_" + i + "_0";
    }

    public static String toString(TeacherCourse record) {
        LocalTime time1 = record.getStartTime();
        LocalTime time2 = record.getEndTime();
        return time1.format(dateTimeFormatter) + "-" + time2.format(dateTimeFormatter);
    }
}
