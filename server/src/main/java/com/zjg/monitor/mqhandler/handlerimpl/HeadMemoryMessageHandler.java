package com.zjg.monitor.mqhandler.handlerimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjg.monitor.entity.HeadMemory;
import com.zjg.monitor.entity.Systems;
import com.zjg.monitor.mqhandler.AbstractMessageHandler;
import com.zjg.monitor.response.BaseMessage;
import com.zjg.monitor.response.HeadMemoryResult;
import com.zjg.monitor.service.HeadMemoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zjg
 * <p> 2020/4/13 13:59 </p>
 */
@Slf4j
@Service
public class HeadMemoryMessageHandler extends AbstractMessageHandler {

    private static final String HEAD_MEMORY_RATE = "headMemoryRate";

    @Resource
    private HeadMemoryService headMemoryService;

    @Override
    public void receiveMessage(JSONObject message) {
        if (message.get("msgType").equals(BaseMessage.MsgType.HEAD_MEMORY.toString())) {
            handlerMessage(message);
        } else if (super.nextHandler == null) {
            log.error("这个消息处理不了，可能发生了异常，请关注");
        } else {
            super.nextHandler.receiveMessage(message);
        }
    }

    @Override
    protected void handlerMessage(JSONObject message) {
        HeadMemoryResult headMemoryResult = JSON.toJavaObject(message, HeadMemoryResult.class);
        Systems systems = getSystems(headMemoryResult.getSystem());
        //堆内存使用率报警
        thresholdAudio(systems, headMemoryResult.getMsgType().toString(), HEAD_MEMORY_RATE, headMemoryResult.getHeadMemoryRate(), headMemoryResult.getCurrTime(), true);
        HeadMemory headMemory = HeadMemory.builder()
                .system(headMemoryResult.getSystem())
                .currTime(headMemoryResult.getCurrTime())
                .maxMemory(headMemoryResult.getMaxMemory())
                .initMemory(headMemoryResult.getInitMemory())
                .usedMemory(headMemoryResult.getUsedMemory())
                .headMemoryRate(headMemoryResult.getHeadMemoryRate())
                .build();
        headMemoryService.insert(headMemory);
    }

}
