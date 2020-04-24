package com.zjg.monitor.mqhandler;

import com.alibaba.fastjson.JSONObject;
import com.zjg.monitor.entity.AudioRecord;
import com.zjg.monitor.entity.Systems;
import com.zjg.monitor.entity.Threshold;
import com.zjg.monitor.response.BaseMessage;
import com.zjg.monitor.service.AudioRecordService;
import com.zjg.monitor.service.SystemService;
import com.zjg.monitor.service.ThresholdService;
import com.zjg.monitor.util.EmailUtil;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zjg
 * <p> 2020/4/4 16:14 </p>
 */
public abstract class AbstractMessageHandler {


    @Resource
    protected ThresholdService thresholdService;
    @Resource
    protected SystemService systemService;
    @Resource
    protected EmailUtil emailUtil;
    @Resource
    protected AudioRecordService audioRecordService;

    /**
     * 下一个handler
     */
    protected AbstractMessageHandler nextHandler;

    /**
     * 接收并判断是否自己可处理
     */
    public abstract void receiveMessage(JSONObject message);

    /**
     * 可以处理的话进行处理
     */
    protected abstract void handlerMessage(JSONObject message);


    /**
     * 构建报警记录
     */
    private AudioRecord builderAudioRecord(Systems systems, Date currTime, String msgType, String monitorKey, double nowValue, double threshold) {
        return AudioRecord.builder()
                .systemId(systems.getId())
                .systemName(systems.getSystemName())
                .audioTime(currTime)
                .audioType(msgType)
                .audioKey(monitorKey)
                .nowValue(String.valueOf(nowValue))
                .thresholdValue(String.valueOf(threshold))
                .build();
    }

    /**
     * 进行阈值对比和报警
     */
    protected void thresholdAudio(Systems system, String msgType, String monitorKey, double nowValue, Date currTime, boolean bigOrSmaller) {
        //进行阈值对比
        Threshold threshold = thresholdService.selectBySystemAndKey(system.getSystemName(), msgType, monitorKey);
        //报警
        if (!system.isStopAudio() && threshold != null && nowValue > threshold.getThreshold()) {
            if ((bigOrSmaller && nowValue > threshold.getThreshold())
                    || (!bigOrSmaller && nowValue < threshold.getThreshold())) {
                emailUtil.audio(system.getEmails(), system.getSystemName(), msgType, threshold.getMonitorKey(), nowValue, threshold.getThreshold());
                AudioRecord audioRecord = builderAudioRecord(system, currTime, msgType, monitorKey, nowValue, threshold.getThreshold());
                audioRecordService.insert(audioRecord);
                systemService.addAudioCount(system);
            }
        }

    }

    /**
     * 获得或者创建systems
     */
    protected Systems getSystems(String system) {
        Systems systems = systemService.selectBySystemName(system);
        if (systems == null) {
            systems = new Systems(system, "");
            systemService.insert(systems);
        }
        return systems;
    }

    public AbstractMessageHandler getNextHandler() {
        return nextHandler;
    }

    public void setNextHandler(AbstractMessageHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
