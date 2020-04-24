package com.zjg.monitor.service;

import com.zjg.monitor.entity.DiskParent;

/**
 * @author zjg
 * <p> 2020/4/5 13:10 </p>
 */
public interface DiskParentService {


    DiskParent selectBySystemName(String system);

    void insert(DiskParent diskParent);

    void updateDiskParent(DiskParent diskParent);
}
