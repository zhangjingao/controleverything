package com.zjg.monitor.service;

import com.zjg.monitor.entity.GC;
import com.zjg.monitor.toresult.ChartsResult;

import java.util.Date; /**
 * @author zjg
 * <p> 2020/4/13 13:57 </p>
 */
public interface GcService {


    boolean insert(GC gc);

    ChartsResult getData(String system, Date startTime, Date stopTime);
}
