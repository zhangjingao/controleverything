package com.zjg.monitor.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjg.monitor.entity.Systems;
import com.zjg.monitor.service.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zjg
 * <p> 2020/4/3 15:21 </p>
 */
@Slf4j
@RestController
@RequestMapping("system")
public class SystemController {

    @Resource
    private SystemService systemService;

    @GetMapping("select")
    public IPage<Systems> selectSystem(@RequestParam int pageNum,
                                       @RequestParam int pageSize,
                                       @RequestParam String systemName) {
        return systemService.select(pageNum, pageSize, systemName);
    }

    @GetMapping("select/all")
    public List<Systems> selectAll(){
        return systemService.selectAll();
    }

    @GetMapping("select/{id}")
    public Systems selectSystem(@PathVariable int id){
        return systemService.select(id);
    }

    @PostMapping("update")
    public boolean updateSystem (@RequestParam Integer id,
                                 @RequestParam String emails,
                                 @RequestParam boolean stopAudio) {
        log.error(id  + " " + emails + " " + stopAudio);
        if (id == null || id == 0) {
            log.error("修改失败，未选中id");
            return false;
        }
        if (StringUtils.isEmpty(emails)) {
            log.error("修改失败，负责人邮件都为空");
            return false;
        }
        return systemService.updateSystem(id, emails, stopAudio);
    }

}
