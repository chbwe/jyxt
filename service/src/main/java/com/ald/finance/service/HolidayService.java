package com.ald.finance.service;

import com.ald.finance.dal.entity.Holiday;
import com.ald.finance.dal.repository.HolidayRepository;
import com.ald.finance.common.web.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by liang3.zhang on 2018/5/3.
 */
@Service
public class HolidayService {

    @Autowired
    HolidayRepository holidayRepository;

    /**
     * 节假日记录
     *
     * @param
     * @param pageable
     * @return
     */
    public Page<Holiday> list(Pageable pageable) {
        return holidayRepository.findAll(pageable);
    }

    /**
     * 添加节假日
     *
     * @param
     * @return
     */

    public Boolean add(Holiday holiday) {
        Holiday holiday1 = holidayRepository.findOneByHolidayDay(holiday.getHolidayDay());
        if (holiday1 != null) {
            throw new BusinessException("节假日已存在");
        }
        if (holiday.getHolidayDay().isBefore(LocalDate.now())) {
            throw new BusinessException("只能添加今天以后的节假日");
        }
        holiday.setUpdateTime(LocalDateTime.now());
        holiday.setCreateTime(LocalDateTime.now());
        holidayRepository.save(holiday);
        return true;
    }

    /**
     * 删除节假日
     *
     * @param id
     * @return
     */

    public Boolean delete(Long id) {
        Holiday holiday = holidayRepository.findOne(id);
        if (holiday == null) {
            throw new BusinessException("节假日不存在");
        }
        if (holiday.getHolidayDay().isAfter(LocalDate.now())) {
            throw new BusinessException("只能删除今天以后的节假日");
        }
        holidayRepository.delete(holiday);
        return true;
    }
}
