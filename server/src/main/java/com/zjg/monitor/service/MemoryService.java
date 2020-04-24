package com.zjg.monitor.service;

import com.zjg.monitor.entity.Memory;
import com.zjg.monitor.toresult.ChartsResult;

import java.util.Date; /**
 * @author zjg
 * <p> 2020/4/13 14:11 </p>
 */
public interface MemoryService {

    boolean insert(Memory memory);

    ChartsResult getData(String system, Date startTime, Date stopTime);
}
