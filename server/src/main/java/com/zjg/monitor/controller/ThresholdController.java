package com.zjg.monitor.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjg.monitor.entity.Systems;
import com.zjg.monitor.entity.Threshold;
import com.zjg.monitor.service.ThresholdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zjg
 * <p> 2020/4/3 20:12 </p>
 */
@Slf4j
@RestController
@RequestMapping("threshold")
public class ThresholdController {

    @Resource
    private ThresholdService thresholdService;


    @GetMapping("select")
    public IPage<Threshold> selectThresholdAll(@RequestParam int pageNum,
                                             @RequestParam int pageSize,
                                             @RequestParam String systemName) {
        return thresholdService.selectThreshold(pageNum, pageSize, systemName);
    }

    @GetMapping("select/{id}")
    public Threshold selectById(@PathVariable("id") int id) {
        return thresholdService.selectById(id);
    }

    @PostMapping("insert")
    public boolean insert(@RequestParam int systemId,
                          @RequestParam String systemName,
                          @RequestParam String monitorType,
                          @RequestParam String monitorKey,
                          @RequestParam double threshold
                          ){
        if (systemId == 0 || StringUtils.isEmpty(systemName) ||
                StringUtils.isEmpty(monitorType) || StringUtils.isEmpty(monitorKey)|| threshold < 0) {
            log.error("插入数据请求不全");
            return false;
        }
        Threshold thresholdObj = new Threshold(systemId, systemName, monitorType, monitorKey, threshold);
        return thresholdService.insert(thresholdObj);
    }

    @PostMapping("update")
    public boolean update(@RequestParam int id,
                          @RequestParam double threshold){
        return thresholdService.update(id, threshold);
    }

    @PostMapping("delete/{id}")
    public boolean deleteById(@PathVariable int id){
        return thresholdService.deleteById(id);
    }


}
