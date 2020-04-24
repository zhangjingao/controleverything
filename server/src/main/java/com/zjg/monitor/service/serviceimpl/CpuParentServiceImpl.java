package com.zjg.monitor.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjg.monitor.dao.CpuParentMapper;
import com.zjg.monitor.entity.CpuParent;
import com.zjg.monitor.service.CpuParentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zjg
 * <p> 2020/4/4 19:39 </p>
 */
@Service
public class CpuParentServiceImpl implements CpuParentService {

    @Resource
    private CpuParentMapper cpuParentMapper;

    @Override
    public CpuParent selectBySystem(String system) {
        QueryWrapper<CpuParent> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("system", system);
        return cpuParentMapper.selectOne(queryWrapper);
    }

    @Override
    public void insert(CpuParent cpuParent) {
        cpuParentMapper.insert(cpuParent);
    }

    @Override
    public void update(CpuParent cpuParent) {
        cpuParentMapper.updateById(cpuParent);
    }
}
