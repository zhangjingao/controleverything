package com.zjg.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjg.monitor.entity.Threads;
import com.zjg.monitor.toresult.ChartsResult;

import java.util.Date; /**
 * @author zjg
 * <p> 2020/4/13 14:12 </p>
 */
public interface ThreadsService {


    boolean insert(Threads threads);

    ChartsResult getData(String system, Date startTime, Date stopTime);

    IPage<Threads> select(int pageNum, int pageSize, String systemName);
}
