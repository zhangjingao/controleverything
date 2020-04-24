package com.zjg.monitor.service;

import com.zjg.monitor.entity.HeadMemory;
import com.zjg.monitor.toresult.ChartsResult;

import java.util.Date; /**
 * @author zjg
 * <p> 2020/4/13 14:09 </p>
 */
public interface HeadMemoryService {


    boolean insert(HeadMemory headMemory);

    ChartsResult getData(String system, Date startTime, Date stopTime);
}
