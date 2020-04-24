package com.zjg.monitor.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjg.monitor.dao.GCMapper;
import com.zjg.monitor.entity.GC;
import com.zjg.monitor.service.GcService;
import com.zjg.monitor.toresult.ChartsResult;
import com.zjg.monitor.util.TimeBuilderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.constraints.Null;
import java.util.*;

/**
 * @author zjg
 * <p> 2020/4/13 13:57 </p>
 */
@Slf4j
@Service
public class GcServiceImpl implements GcService {

    @Resource
    private GCMapper gcMapper;

    @Override
    public boolean insert(GC gc) {
        return gcMapper.insert(gc) == 1;
    }

    @Override
    public ChartsResult getData(String system, Date startTime, Date stopTime) {
        ChartsResult chartsResult = new ChartsResult();
        try {
            //插入序列
            chartsResult.setSeries(Collections.singletonList("gc次数"));
            List<Date> dates = TimeBuilderUtil.buildTime(startTime, stopTime);
            //横坐标
            chartsResult.setXDatas(dates);
            QueryWrapper<GC> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("system", system);
            queryWrapper.in("curr_time", dates);
            List<GC> gcs = gcMapper.selectList(queryWrapper);
            List<Double> yDatas = new LinkedList<>();
            //无需补充null的
            if (gcs.size() == dates.size()) {
                for (GC gc : gcs) {
                    yDatas.add((double) gc.getGcCount());
                }
            } else {
                List<Date> dateList = new LinkedList<>();
                for (GC gc : gcs) {
                    dateList.add(gc.getCurrTime());
                }
                for (Date date : dates) {
                    if (dateList.contains(date)) {
                        yDatas.add((double) gcs.get(dateList.indexOf(date)).getGcCount());
                    } else {
                        yDatas.add(null);
                    }
                }
            }
            Map<String, List<Double>> series = new HashMap<>();
            series.put("gc次数", yDatas);
            chartsResult.setYDatas(series);
            return chartsResult;
        } catch (Exception e) {
            log.error("获取gc数据异常", e);
            chartsResult.setCode(500);
            chartsResult.setMsg(e.getMessage());
        }
        return chartsResult;
    }
}
