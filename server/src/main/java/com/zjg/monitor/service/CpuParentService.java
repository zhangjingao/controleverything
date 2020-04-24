package com.zjg.monitor.service;

import com.zjg.monitor.entity.CpuParent;

/**
 * @author zjg
 * <p> 2020/4/4 19:39 </p>
 */
public interface CpuParentService {

    /**
     * 根据系统名查询cpuParent
     */
    CpuParent selectBySystem(String system);

    /**
     * 插入
     */
    void insert(CpuParent cpuParent);

    void update(CpuParent cpuParent);
}
