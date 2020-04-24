package com.zjg.monitor.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjg.monitor.dao.ThresholdMapper;
import com.zjg.monitor.entity.Systems;
import com.zjg.monitor.entity.Threshold;
import com.zjg.monitor.service.ThresholdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zjg
 * <p> 2020/4/3 19:50 </p>
 */
@Slf4j
@Service
public class ThresholdServiceImpl implements ThresholdService {

    @Resource
    private ThresholdMapper thresholdMapper;

    @Override
    public boolean insert(Threshold threshold) {
        QueryWrapper<Threshold> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("system_name", threshold.getSystemName());
        queryWrapper.eq("monitor_type", threshold.getMonitorType());
        queryWrapper.eq("monitor_key", threshold.getMonitorKey());
        if (thresholdMapper.selectOne(queryWrapper) != null) {
            log.error("插入失败，该监控已存在");
            return false;
        }
        return thresholdMapper.insert(threshold) == 1;
    }

    @Override
    public boolean update(int id, double thresholdValue) {
        Threshold threshold = new Threshold(id, thresholdValue);
        return thresholdMapper.updateById(threshold) == 1;
    }

    @Override
    public boolean deleteById(int id) {
        return thresholdMapper.deleteById(id) == 1;
    }

    @Override
    public Threshold selectById(int id) {
        return thresholdMapper.selectById(id);
    }

    @Override
    public Threshold selectBySystemAndKey(String system, String type, String key) {
        QueryWrapper<Threshold> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("system_name", system);
        queryWrapper.eq("monitor_type", type);
        queryWrapper.eq("monitor_key", key);
        return thresholdMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Threshold> selectThreshold(int pageNum, int pageSize, String systemName) {
        QueryWrapper<Threshold> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(systemName)) {
            queryWrapper.like("system_name",systemName);
        }
        return thresholdMapper.selectPage(new Page<>(pageNum, pageSize), queryWrapper);
    }
}
