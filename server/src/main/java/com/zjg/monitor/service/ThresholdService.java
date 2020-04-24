package com.zjg.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjg.monitor.entity.Systems;
import com.zjg.monitor.entity.Threshold;

import java.util.List;

/**
 * @author zjg
 * <p> 2020/4/3 19:43 </p>
 */
public interface ThresholdService {

    boolean insert(Threshold threshold);

    boolean update(int id, double threshold);

    boolean deleteById(int id);


    Threshold selectById(int id);

    /**
     * 根据系统、监控类型、监控属性得到阈值信息
     */
    Threshold selectBySystemAndKey(String system, String type, String key);

    IPage<Threshold> selectThreshold(int pageNum, int pageSize, String systemName);
}
