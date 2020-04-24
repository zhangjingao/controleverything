package com.zjg.monitor.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjg.monitor.dao.DiskMapper;
import com.zjg.monitor.dao.DiskParentMapper;
import com.zjg.monitor.entity.Disk;
import com.zjg.monitor.entity.DiskParent;
import com.zjg.monitor.service.DiskService;
import com.zjg.monitor.toresult.DiskChartsResult;
import com.zjg.monitor.util.TimeBuilderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author zjg
 * <p> 2020/4/5 13:10 </p>
 */
@Slf4j
@Service
public class DiskServiceImpl implements DiskService {

    @Resource
    private DiskMapper diskMapper;
    @Resource
    private DiskParentMapper diskParentMapper;

    @Override
    public void insert(Disk disk) {
        diskMapper.insert(disk);
    }

    @Override
    public DiskChartsResult getData(String system, Date startTime, Date stopTime) {
        DiskChartsResult diskChartsResult = new DiskChartsResult();
        try{
            QueryWrapper<DiskParent> diskParentQueryWrapper = new QueryWrapper<>();
            diskParentQueryWrapper.eq("system", system);
            DiskParent diskParent = diskParentMapper.selectOne(diskParentQueryWrapper);
            if (diskParent == null) {
                return new DiskChartsResult(500, "diskParent");
            }
            String[] devNames = diskParent.getDevNames().split(",");
            List<Date> dates = TimeBuilderUtil.buildTime(startTime, stopTime);
            List<String> series = Arrays.asList("可用空间", "已经使用的空间", "空间利用率");
            List<DiskChartsResult.ECharts> eChartsList = new LinkedList<>();
            for (String devName: devNames) {
                DiskChartsResult.ECharts eCharts = new DiskChartsResult.ECharts();
                eCharts.setChartsName(devName);
                eCharts.setSeries(series);
                eCharts.setXDatas(dates);
                QueryWrapper<Disk> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("system", system);
                queryWrapper.eq("dev_name",devName);
                queryWrapper.in("curr_time", dates);
                List<Disk> disks = diskMapper.selectList(queryWrapper);
                List<Double> canUseSpace = new LinkedList<>();
                List<Double> usedSpace = new LinkedList<>();
                List<Double> spaceRate = new LinkedList<>();
                for (Disk disk : disks){
                    canUseSpace.add(disk.getCanUseSpace());
                    usedSpace.add(disk.getUsedSpace());
                    spaceRate.add(disk.getSpaceRate());
                }
                Map<String, List<Double>> yDatas = new HashMap<>();
                yDatas.put("可用空间", canUseSpace);
                yDatas.put("已经使用的空间", usedSpace);
                yDatas.put("空间利用率", spaceRate);
                eCharts.setYDatas(yDatas);
                eChartsList.add(eCharts);
            }
            diskChartsResult.setECharts(eChartsList);
            diskChartsResult.setCode(200);
        } catch (Exception e){
            log.error("disk获取数据异常", e);
        }
        return diskChartsResult;
    }
}
