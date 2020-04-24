package com.zjg.monitor.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjg.monitor.dao.HeadMemoryMapper;
import com.zjg.monitor.entity.HeadMemory;
import com.zjg.monitor.service.HeadMemoryService;
import com.zjg.monitor.toresult.ChartsResult;
import com.zjg.monitor.util.TimeBuilderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zjg
 * <p> 2020/4/13 14:09 </p>
 */
@Slf4j
@Service
public class HeadMemoryServiceImpl implements HeadMemoryService {

    @Resource
    private HeadMemoryMapper headMemoryMapper;

    @Override
    public boolean insert(HeadMemory headMemory) {
        return headMemoryMapper.insert(headMemory) == 1;
    }

    @Override
    public ChartsResult getData(String system, Date startTime, Date stopTime) {
        ChartsResult chartsResult = new ChartsResult();
        try {
            chartsResult.setSeries(Arrays.asList("初始化堆内存", "已经使用的堆内存", "堆内存使用率"));
            List<Date> dates = TimeBuilderUtil.buildTime(startTime, stopTime);
            chartsResult.setXDatas(dates);
            QueryWrapper<HeadMemory> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("system", system);
            queryWrapper.in("curr_time", dates);
            List<HeadMemory> memories = headMemoryMapper.selectList(queryWrapper);
            Map<String, List<Double>> yDatas = new HashMap<>();
            List<Double> initMemory = new LinkedList<>();
            List<Double> usedMemory = new LinkedList<>();
            List<Double> headMemoryRate = new LinkedList<>();
            if (memories.size() == dates.size()) {
                for (HeadMemory memory : memories) {
                    initMemory.add(memory.getInitMemory());
                    usedMemory.add(memory.getUsedMemory());
                    headMemoryRate.add(memory.getHeadMemoryRate());
                }
            } else {
                List<Date> dateList = new LinkedList<>();
                for (HeadMemory memory : memories) {
                    dateList.add(memory.getCurrTime());
                }
                for (Date date : dates) {
                    if (dateList.contains(date)) {
                        initMemory.add(memories.get(dateList.indexOf(date)).getInitMemory());
                        usedMemory.add(memories.get(dateList.indexOf(date)).getUsedMemory());
                        headMemoryRate.add(memories.get(dateList.indexOf(date)).getHeadMemoryRate());
                    } else {
                        initMemory.add(null);
                        usedMemory.add(null);
                        headMemoryRate.add(null);
                    }
                }
            }
            yDatas.put("初始化堆内存", initMemory);
            yDatas.put("已经使用的内存", usedMemory);
            yDatas.put("堆内存使用率", headMemoryRate);
            chartsResult.setYDatas(yDatas);
            chartsResult.setCode(200);
        } catch (Exception e) {
            log.error("获取堆内存数据异常", e);
            chartsResult.setCode(500);
            chartsResult.setMsg(e.getMessage());
        }
        return chartsResult;
    }
}
