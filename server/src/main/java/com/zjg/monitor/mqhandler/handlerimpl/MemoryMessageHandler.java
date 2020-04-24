package com.zjg.monitor.mqhandler.handlerimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjg.monitor.entity.Memory;
import com.zjg.monitor.entity.Systems;
import com.zjg.monitor.mqhandler.AbstractMessageHandler;
import com.zjg.monitor.response.BaseMessage;
import com.zjg.monitor.response.MemoryResult;
import com.zjg.monitor.service.MemoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zjg
 * <p> 2020/4/13 14:13 </p>
 */
@Slf4j
@Service
public class MemoryMessageHandler extends AbstractMessageHandler {

    private static final String MEMORY_RATE = "memoryRate";
    @Resource
    private MemoryService memoryService;

    @Override
    public void receiveMessage(JSONObject message) {
        if (message.get("msgType").equals(BaseMessage.MsgType.MEMORY.toString())) {
            handlerMessage(message);
        } else if (super.nextHandler == null) {
            log.error("这个消息处理不了，可能发生了异常，请关注");
        } else {
            super.nextHandler.receiveMessage(message);
        }
    }

    @Override
    protected void handlerMessage(JSONObject message) {
        MemoryResult memoryResult = JSON.toJavaObject(message, MemoryResult.class);
        Systems systems = getSystems(memoryResult.getSystem());
        thresholdAudio(systems, memoryResult.getMsgType().toString(), MEMORY_RATE, memoryResult.getMemoryRate(), memoryResult.getCurrTime(), true);
        Memory memory = Memory.builder()
                .system(systems.getSystemName())
                .currTime(memoryResult.getCurrTime())
                .totalMemory(memoryResult.getTotalMemory())
                .freeMemory(memoryResult.getFreeMemory())
                .usedMemory(memoryResult.getUsedMemory())
                .memoryRate(memoryResult.getMemoryRate()).build();
        memoryService.insert(memory);
    }

}
