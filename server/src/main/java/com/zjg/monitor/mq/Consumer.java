package com.zjg.monitor.mq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjg.monitor.mqhandler.AbstractMessageHandler;
import com.zjg.monitor.mqhandler.handlerimpl.*;
import com.zjg.monitor.response.BaseMessage;
import com.zjg.monitor.service.HeadMemoryService;
import com.zjg.monitor.util.Contant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author zjg
 * <p> 2020/4/1 11:49 </p>
 */
@Slf4j
@Component
public class Consumer {

    @Resource
    private ErrorMessageHandler errorMessageHandler;
    @Resource
    private CpuMessageHandler cpuMessageHandler;
    @Resource
    private DiskMessageHandler diskMessageHandler;
    @Resource
    private GcMessageHandler gcMessageHandler;
    @Resource
    private HeadMemoryMessageHandler headMemoryMessageHandler;
    @Resource
    private MemoryMessageHandler memoryMessageHandler;
    @Resource
    private ThreadsMessageHandler threadsMessageHandler;

    @KafkaListener(topics = {"monitor"})
    public void listen (String message) {
        log.warn("has msg: {}", message);
        Optional optional = Optional.ofNullable(message);
        assert optional.isPresent();
        JSONObject jsonObject = JSON.parseObject(message);
        //改为消费json
        handlerMessage(jsonObject);
    }

    private void handlerMessage(JSONObject baseMessage){
        AbstractMessageHandler messageHandler = builderMessageHandler();
        messageHandler.receiveMessage(baseMessage);
    }

    /**
     * 构造责任链
     */
    private AbstractMessageHandler builderMessageHandler() {
        errorMessageHandler.setNextHandler(cpuMessageHandler);
        cpuMessageHandler.setNextHandler(diskMessageHandler);
        diskMessageHandler.setNextHandler(memoryMessageHandler);
        memoryMessageHandler.setNextHandler(gcMessageHandler);
        gcMessageHandler.setNextHandler(headMemoryMessageHandler);
        headMemoryMessageHandler.setNextHandler(threadsMessageHandler);
        return errorMessageHandler;
    }

}
