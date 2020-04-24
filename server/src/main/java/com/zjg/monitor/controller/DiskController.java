package com.zjg.monitor.controller;

import com.zjg.monitor.service.DiskService;
import com.zjg.monitor.toresult.ChartsResult;
import com.zjg.monitor.toresult.DiskChartsResult;
import com.zjg.monitor.util.TimeBuilderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zjg
 * <p> 2020/4/17 15:20 </p>
 */
@Slf4j
@RestController
@RequestMapping("disk")
public class DiskController {

    @Resource
    private DiskService diskService;


    @ResponseBody
    @PostMapping("getdata")
    public DiskChartsResult getData(@RequestParam String system,
                                    @RequestParam String timeRange){
        if (StringUtils.isEmpty(system) || StringUtils.isEmpty(timeRange)) {
            return new DiskChartsResult(500, "请输入系统名和时间");
        }
        try {
            Date[] time = TimeBuilderUtil.timeFormater(timeRange);
            return diskService.getData(system, time[0], time[1]);
        } catch (Exception e) {
            return new DiskChartsResult(500, "查询异常，" + e.getMessage());
        }
    }

}
