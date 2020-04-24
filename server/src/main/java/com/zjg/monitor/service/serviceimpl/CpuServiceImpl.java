package com.zjg.monitor.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjg.monitor.dao.CpuMapper;
import com.zjg.monitor.dao.CpuParentMapper;
import com.zjg.monitor.entity.Cpu;
import com.zjg.monitor.entity.CpuParent;
import com.zjg.monitor.service.CpuService;
import com.zjg.monitor.toresult.ChartsResult;
import com.zjg.monitor.util.TimeBuilderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zjg
 * <p> 2020/4/4 19:38 </p>
 */
@Slf4j
@Service
public class CpuServiceImpl implements CpuService {

    @Resource
    private CpuMapper cpuMapper;
    @Resource
    private CpuParentMapper cpuParentMapper;

    @Override
    public void insert(Cpu cpu) {
        cpuMapper.insert(cpu);
    }

    @Override
    public ChartsResult getData(Date startTime, Date stopTime, String system) {
        ChartsResult chartsResult = new ChartsResult();
        try {
            QueryWrapper<CpuParent> cpuParentQueryWrapper = new QueryWrapper<>();
            cpuParentQueryWrapper.eq("system", system);
            CpuParent cpuParent = cpuParentMapper.selectOne(cpuParentQueryWrapper);
            if (cpuParent == null) {
                chartsResult.setCode(500);
                chartsResult.setMsg("系统不存在");
                return chartsResult;
            }
            String[] cpuIdArr = cpuParent.getCpuIds().split(",");
            //序列
            List<String> series = new ArrayList<>(Arrays.asList(cpuIdArr));

            List<Date> dates = TimeBuilderUtil.buildTime(startTime, stopTime);
            chartsResult.setSeries(series);
            chartsResult.setXDatas(dates);
            Map<String, List<Double>> yDatas = new HashMap<>();
            for (String cpuId: series) {
                QueryWrapper<Cpu> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("system", system);
                queryWrapper.eq("cpu_id", Integer.parseInt(cpuId));
                queryWrapper.in("curr_time", dates);
                List<Cpu> cpus = cpuMapper.selectList(queryWrapper);
                if (dates.size() == cpus.size()) {
                    List<Double> cpuRates = new LinkedList<>();
                    for (Cpu cpu : cpus) {
                        cpuRates.add(cpu.getCpuRate());
                    }
                    yDatas.put(cpuId, cpuRates);
                } else {
                    List<Double> cpuRatesUpdate = new LinkedList<>();
                    List<Date> dateList = new LinkedList<>();
                    for (Cpu cpu : cpus) {
                        dateList.add(cpu.getCurrTime());
                    }
                    for (Date date : dates) {
                        if (dateList.contains(date)) {
                            cpuRatesUpdate.add(cpus.get(dateList.indexOf(date)).getCpuRate());
                        } else {
                            cpuRatesUpdate.add(null);
                        }
                    }
                    yDatas.put(cpuId, cpuRatesUpdate);
                }
            }
            chartsResult.setYDatas(yDatas);
            chartsResult.setCode(200);
        } catch (Exception e) {
            log.error("获取cpu监控信息异常", e);
            chartsResult.setCode(500);
            chartsResult.setMsg(e.getMessage());
        }
        return chartsResult;
    }

}
