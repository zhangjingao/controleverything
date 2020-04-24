package com.zjg.monitor.mqhandler.handlerimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjg.monitor.entity.*;
import com.zjg.monitor.mqhandler.AbstractMessageHandler;
import com.zjg.monitor.response.BaseMessage;
import com.zjg.monitor.response.CpuResult;
import com.zjg.monitor.service.CpuParentService;
import com.zjg.monitor.service.CpuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author zjg
 * <p> 2020/4/4 16:17 </p>
 */
@Slf4j
@Service
public class CpuMessageHandler extends AbstractMessageHandler {

    @Resource
    private CpuParentService cpuParentService;
    @Resource
    private CpuService cpuService;

    private static final String MONITOR_KEY = "average";


    @Override
    public void receiveMessage(JSONObject message) {
        if (message.get("msgType").equals(BaseMessage.MsgType.CPU.toString())) {
            handlerMessage(message);
        } else if (super.nextHandler == null) {
            log.error("这个消息处理不了，可能发生了异常，请关注");
        } else {
            super.nextHandler.receiveMessage(message);
        }
    }


    @Override
    protected void handlerMessage(JSONObject message) {
        //比较阈值，有可能报警，然后当前值落库，报警落库
        CpuResult cpuResult = JSON.toJavaObject(message,CpuResult.class);
        //进行cpu编号对比
        int cpuSize = cpuResult.getCpuRates().size();
        String system = cpuResult.getSystem();
        Systems systems = getSystems(system);

        CpuParent cpuParent = cpuParentService.selectBySystem(system);
        if (cpuParent == null) {
            String cpuIds = builderCpuIds(cpuSize);
            cpuParent = new CpuParent(system, cpuIds);
            cpuParentService.insert(cpuParent);
        }
        String cpuIds = cpuParent.getCpuIds();
        String[] cpuIdArr = cpuIds.split(",");
        if (cpuSize != cpuIdArr.length) {
            cpuIds = builderCpuIds(cpuSize);
            cpuParent.setCpuIds(cpuIds);
            cpuParentService.update(cpuParent);
        }

        double average = 0;
        for (int i = 0; i < cpuSize; i++) {
            String rateStr = cpuResult.getCpuRates().get(i);
            double rate = Double.parseDouble(rateStr.split("%")[0]);
            average += rate;
        }
        average = average / cpuSize;

        thresholdAudio(systems, cpuResult.getMsgType().toString(), MONITOR_KEY, average, cpuResult.getCurrTime(), true);

        //进行落库
        for (int i = 0; i < cpuSize; i++) {
            Double rate = Double.parseDouble(cpuResult.getCpuRates().get(i).split("%")[0]);
            Cpu cpu = new Cpu(cpuResult.getSystem(), cpuResult.getCurrTime(), i + 1, rate);
            cpuService.insert(cpu);
        }

    }

    public static void main(String[] args) {
    }


    /**
     * 根据cpu个数构建cpu编号
     */
    private String builderCpuIds (int cpuSize) {
        StringBuilder cpuIds = new StringBuilder();
        for (int i = 1; i <= cpuSize; i++) {
            cpuIds.append(i).append(",");
        }
        return cpuIds.toString();
    }


}
