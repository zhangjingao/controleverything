package com.zjg.monitor.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjg.monitor.dao.MemoryMapper;
import com.zjg.monitor.entity.Memory;
import com.zjg.monitor.service.MemoryService;
import com.zjg.monitor.toresult.ChartsResult;
import com.zjg.monitor.util.TimeBuilderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zjg
 * <p> 2020/4/13 14:11 </p>
 */
@Slf4j
@Service
public class MemoryServiceImpl implements MemoryService {

    @Resource
    private MemoryMapper memoryMapper;

    @Override
    public boolean insert(Memory memory) {
        return memoryMapper.insert(memory) == 1;
    }

    @Override
    public ChartsResult getData(String system, Date startTime, Date stopTime) {
        ChartsResult chartsResult = new ChartsResult();
        try {
            chartsResult.setSeries(Arrays.asList("可用内存", "已经使用的内存", "内存利用率"));
            List<Date> dates = TimeBuilderUtil.buildTime(startTime, stopTime);
            chartsResult.setXDatas(dates);
            QueryWrapper<Memory> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("system", system);
            queryWrapper.in("curr_time", dates);
            List<Memory> memories = memoryMapper.selectList(queryWrapper);
            Map<String, List<Double>> yDatas = new HashMap<>();
            List<Double> freeMemory = new LinkedList<>();
            List<Double> usedMemory = new LinkedList<>();
            List<Double> memoryRate = new LinkedList<>();
            if (memories.size() == dates.size()) {
                for (Memory memory : memories) {
                    freeMemory.add(memory.getFreeMemory());
                    usedMemory.add(memory.getUsedMemory());
                    memoryRate.add(memory.getMemoryRate());
                }
            } else {
                List<Date> dateList = new LinkedList<>();
                for (Memory memory : memories) {
                    dateList.add(memory.getCurrTime());
                }
                for (Date date : dates) {
                    if (dateList.contains(date)) {
                        freeMemory.add(memories.get(dateList.indexOf(date)).getFreeMemory());
                        usedMemory.add(memories.get(dateList.indexOf(date)).getUsedMemory());
                        memoryRate.add(memories.get(dateList.indexOf(date)).getMemoryRate());
                    } else {
                        freeMemory.add(null);
                        usedMemory.add(null);
                        memoryRate.add(null);
                    }
                }
            }
            yDatas.put("可用内存", freeMemory);
            yDatas.put("已经使用的内存", usedMemory);
            yDatas.put("内存利用率", memoryRate);
            chartsResult.setYDatas(yDatas);
            chartsResult.setCode(200);
        } catch (Exception e) {
            log.error("获取内存数据异常", e);
            chartsResult.setCode(500);
            chartsResult.setMsg(e.getMessage());
        }
        return chartsResult;
    }
}
