package com.zjg.monitor.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjg.monitor.dao.DiskParentMapper;
import com.zjg.monitor.entity.DiskParent;
import com.zjg.monitor.service.DiskParentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zjg
 * <p> 2020/4/5 13:10 </p>
 */
@Slf4j
@Service
public class DiskParentServiceImpl implements DiskParentService {

    @Resource
    private DiskParentMapper diskParentMapper;

    @Override
    public DiskParent selectBySystemName(String system) {
        QueryWrapper<DiskParent> diskParentQueryWrapper = new QueryWrapper<>();
        return diskParentMapper.selectOne(diskParentQueryWrapper);
    }

    @Override
    public void insert(DiskParent diskParent) {
        diskParentMapper.insert(diskParent);
    }

    @Override
    public void updateDiskParent(DiskParent diskParent) {
        diskParentMapper.updateById(diskParent);
    }
}
