package com.zjg.monitor.service;

import com.zjg.monitor.entity.Cpu;
import com.zjg.monitor.toresult.ChartsResult;

import java.util.Date; /**
 * @author zjg
 * <p> 2020/4/4 19:38 </p>
 */
public interface CpuService {


    void insert(Cpu cpu);

    ChartsResult getData(Date startTime, Date stopTime, String system);

}
