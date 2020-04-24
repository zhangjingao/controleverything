package com.zjg.monitor.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjg.monitor.dao.ThreadMapper;
import com.zjg.monitor.entity.Threads;
import com.zjg.monitor.service.ThreadsService;
import com.zjg.monitor.toresult.ChartsResult;
import com.zjg.monitor.util.TimeBuilderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zjg
 * <p> 2020/4/13 14:12 </p>
 */
@Slf4j
@Service
public class ThreadsServiceImpl implements ThreadsService {

    @Resource
    private ThreadMapper threadMapper;

    @Override
    public boolean insert(Threads threads) {
        return threadMapper.insert(threads) == 1;
    }

    @Override
    public ChartsResult getData(String system, Date startTime, Date stopTime) {
        ChartsResult chartsResult = new ChartsResult();
        try {
            chartsResult.setSeries(Arrays.asList("线程总数", "守护线程数", "死锁线程"));
            List<Date> dates = TimeBuilderUtil.buildTime(startTime, stopTime);
            //横坐标
            chartsResult.setXDatas(dates);
            QueryWrapper<Threads> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("system", system);
            queryWrapper.in("curr_time", dates);
            List<Threads> threads = threadMapper.selectList(queryWrapper);
            Map<String, List<Double>> yDatas = new HashMap<>();
            List<Double> threadCount = new LinkedList<>();
            List<Double> daemonThreadCount = new LinkedList<>();
            List<Double> deadLockThreadCount = new LinkedList<>();
            if (dates.size() == threads.size()) {
                for (Threads thread : threads) {
                    threadCount.add((double) thread.getThreadCount());
                    daemonThreadCount.add((double) thread.getDaemonThreadCount());
                    deadLockThreadCount.add((double) thread.getDeadLockThreadCount());
                }
            } else {
                List<Date> dateList = new LinkedList<>();
                for (Threads thread : threads) {
                    dateList.add(thread.getCurrTime());
                }
                for (Date date : dates) {
                    if (dateList.contains(date)) {
                        threadCount.add((double) threads.get(dateList.indexOf(date)).getThreadCount());
                        daemonThreadCount.add((double) threads.get(dateList.indexOf(date)).getDaemonThreadCount());
                        deadLockThreadCount.add((double) threads.get(dateList.indexOf(date)).getDeadLockThreadCount());
                    } else {
                        threadCount.add(null);
                        daemonThreadCount.add(null);
                        deadLockThreadCount.add(null);
                    }
                }
            }
            yDatas.put("线程总数", threadCount);
            yDatas.put("守护线程数", daemonThreadCount);
            yDatas.put("死锁线程", deadLockThreadCount);
            chartsResult.setYDatas(yDatas);
            chartsResult.setCode(200);
        } catch (Exception e){
            log.error("获取线程数据异常", e);
            chartsResult.setCode(500);
            chartsResult.setMsg(e.getMessage());
        }
        return chartsResult;
    }

    @Override
    public IPage<Threads> select(int pageNum, int pageSize, String systemName) {
        QueryWrapper<Threads> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("dead_lock_thread_count", 0);
        if (!StringUtils.isEmpty(systemName)) {
            queryWrapper.like("system", systemName);
        }
        Page<Threads> page = new Page<>(pageNum, pageSize);
        return threadMapper.selectPage(page, queryWrapper);
    }
}
