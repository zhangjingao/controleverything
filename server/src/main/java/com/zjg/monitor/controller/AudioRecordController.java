package com.zjg.monitor.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjg.monitor.entity.AudioRecord;
import com.zjg.monitor.entity.Threads;
import com.zjg.monitor.service.AudioRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zjg
 * <p> 2020/4/4 12:15 </p>
 */
@Slf4j
@RestController
@RequestMapping("audiorecord")
public class AudioRecordController {

    @Resource
    private AudioRecordService audioRecordService;

    @GetMapping("select/{id}")
    public AudioRecord selectById(@PathVariable int id) {
        return audioRecordService.selectById(id);
    }

    @GetMapping("select")
    public IPage<AudioRecord> selectSystem(@RequestParam int pageNum,
                                       @RequestParam int pageSize,
                                       @RequestParam String systemName) {
        return audioRecordService.select(pageNum, pageSize, systemName);
    }

    @PostMapping("delete/{id}")
    public boolean delete(@PathVariable int id){
        return audioRecordService.delete(id);
    }

}
