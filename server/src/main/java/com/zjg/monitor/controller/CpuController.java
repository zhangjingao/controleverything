package com.zjg.monitor.controller;

import com.zjg.monitor.service.CpuService;
import com.zjg.monitor.toresult.ChartsResult;
import com.zjg.monitor.util.TimeBuilderUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Date;

/**
 * @author zjg
 * <p> 2020/4/14 19:37 </p>
 */
@Slf4j
@RestController
@RequestMapping("cpu")
public class CpuController {

    @Resource
    private CpuService cpuService;

    /**
     * 根据时间获得cpu信息
     * 180个刻度为最大观看频率
     * 3个小时为界，3小时以内取分钟，3小时以上半小时取一个点
     * 3.75天为界，小时半小时取一个点，大于3.75天取半天
     * 最大观看也就是3个月
     */
    @ResponseBody
    @PostMapping("getdata")
    public ChartsResult getData(@RequestParam String system,
                                @RequestParam String timeRange) {
        if (StringUtils.isEmpty(system) || StringUtils.isEmpty(timeRange)) {
            return new ChartsResult(500, "请输入系统名和时间");
        }
        try {
            Date[] time = TimeBuilderUtil.timeFormater(timeRange);
            return cpuService.getData(time[0], time[1], system);
        } catch (ParseException e) {
            log.error("转换时间及获取cpu数据异常", e);
            return new ChartsResult(500, e.getMessage());
        }
    }

}
