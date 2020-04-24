package com.zjg.monitor.mqhandler.handlerimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjg.monitor.entity.AudioRecord;
import com.zjg.monitor.entity.GC;
import com.zjg.monitor.entity.Systems;
import com.zjg.monitor.entity.Threshold;
import com.zjg.monitor.mqhandler.AbstractMessageHandler;
import com.zjg.monitor.response.BaseMessage;
import com.zjg.monitor.response.GcResult;
import com.zjg.monitor.service.GcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zjg
 * <p> 2020/4/13 13:35 </p>
 */
@Slf4j
@Service
public class GcMessageHandler extends AbstractMessageHandler {


    @Resource
    private GcService gcService;
    private static final String GC_COUNT = "gcCount";

    @Override
    public void receiveMessage(JSONObject message) {
        if (message.get("msgType").equals(BaseMessage.MsgType.GC.toString())) {
            handlerMessage(message);
        } else if (super.nextHandler == null) {
            log.error("这个消息处理不了，可能发生了异常，请关注");
        } else {
            super.nextHandler.receiveMessage(message);
        }
    }

    @Override
    protected void handlerMessage(JSONObject message) {
        GcResult gcResult = JSON.toJavaObject(message, GcResult.class);
        String system = gcResult.getSystem();
        Systems systems = getSystems(system);
        //gc次数报警
        thresholdAudio(systems, gcResult.getMsgType().toString(), GC_COUNT, gcResult.getGcCount(), gcResult.getCurrTime(), true);
        GC gc = new GC(system, gcResult.getCurrTime(), gcResult.getGcCount());
        gcService.insert(gc);
    }

}
