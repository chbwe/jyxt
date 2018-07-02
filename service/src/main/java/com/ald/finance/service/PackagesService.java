package com.ald.finance.service;

import com.ald.finance.dal.entity.Packages;
import com.ald.finance.dal.repository.PackagesRepository;
import com.ald.finance.common.web.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by liang3.zhang on 2018/5/4.
 */
@Service
public class PackagesService {
    
    @Autowired
    PackagesRepository packageRepository;
    
    /**
     * 查询记录
     * 
     * @param pageable
     * @return
     */
    public Page<Packages> list(Pageable pageable) {
        return packageRepository.findAll(pageable);
    }
    
    /**
     * 删除套餐
     * 
     * @param id
     * @return
     */
    public Boolean delete(Long id) {
        Packages packages = packageRepository.findOne(id);
        if (packages == null) {
            throw new BusinessException("此课程套餐已被删除");
        }
        packageRepository.delete(packages);
        return true;
    }
    
    /**
     * 保存
     * 
     * @param packages
     * @return
     */
    public Packages save(Packages packages) {
        if (packages.getId() == null) {
            packages.setCreateTime(LocalDateTime.now());
            packages.setUpdateTime(LocalDateTime.now());
        } else {
            Packages packages1 = load(packages.getId());
            if (packages1 == null) {
                throw new BusinessException("套餐不存在");
            }
            packages.setCreateTime(packages1.getCreateTime());
        }
        packages = packageRepository.save(packages);
        return packages;
    }
    
    /*
     * 获取套餐详细内容
     */
    public Packages load(Long id) {
        return packageRepository.findOne(id);
    }
}
