package com.zjg.monitor.service;

import com.zjg.monitor.entity.Disk;
import com.zjg.monitor.toresult.DiskChartsResult;

import java.util.Date; /**
 * @author zjg
 * <p> 2020/4/5 13:10 </p>
 */
public interface DiskService {

    void insert(Disk disk);

    DiskChartsResult getData(String system, Date startTime, Date stopTime);
}
