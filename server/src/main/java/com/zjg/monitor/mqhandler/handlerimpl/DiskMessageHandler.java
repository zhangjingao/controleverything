package com.zjg.monitor.mqhandler.handlerimpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zjg.monitor.entity.*;
import com.zjg.monitor.mqhandler.AbstractMessageHandler;
import com.zjg.monitor.response.BaseMessage;
import com.zjg.monitor.response.DiskResult;
import com.zjg.monitor.service.DiskParentService;
import com.zjg.monitor.service.DiskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zjg
 * <p> 2020/4/5 12:05 </p>
 */
@Slf4j
@Service
public class DiskMessageHandler extends AbstractMessageHandler {

    @Resource
    private DiskParentService diskParentService;
    @Resource
    private DiskService diskService;
    /**
     * 监控报警属性
     */
    private static final String MONITOR_KEY_CAN_USER_SPACE = "canUseSpace";
    private static final String MONITOR_KEY_SPACE_RATE = "spaceRate";


    @Override
    public void receiveMessage(JSONObject message) {
        if (message.get("msgType").equals(BaseMessage.MsgType.DISK.toString())) {
            handlerMessage(message);
        } else if (super.nextHandler == null) {
            log.error("这个消息处理不了，可能发生了异常，请关注");
        } else {
            super.nextHandler.receiveMessage(message);
        }
    }

    @Override
    protected void handlerMessage(JSONObject message) {
        DiskResult diskResult =  JSON.toJavaObject(message,DiskResult.class);
        Systems systems = getSystems(diskResult.getSystem());

        List<DiskResult.Disk> disks = diskResult.getDisks();
        //查看编号
        int diskSize = disks.size();
        DiskParent diskParent = diskParentService.selectBySystemName(diskResult.getSystem());
        if (diskParent == null) {
            diskParent = new DiskParent(diskResult.getSystem(), builderDisks(disks));
            diskParentService.insert(diskParent);
        }
        String[] devNames= diskParent.getDevNames().split(",");
        if (devNames.length != diskSize) {
            diskParent.setDevNames(builderDisks(disks));
            diskParentService.updateDiskParent(diskParent);
        }

        double canUsedSpaceTotal = 0;
        double spaceRate = 0;
        for (DiskResult.Disk  disk : diskResult.getDisks()) {
            canUsedSpaceTotal += disk.getCanUseSpace();
            spaceRate += disk.getSpaceRate();
        }
        spaceRate = spaceRate / diskSize;
        thresholdAudio(systems, diskResult.getMsgType().toString(), MONITOR_KEY_CAN_USER_SPACE, canUsedSpaceTotal, diskResult.getCurrTime(), false);
        thresholdAudio(systems, diskResult.getMsgType().toString(), MONITOR_KEY_SPACE_RATE, spaceRate, diskResult.getCurrTime(), true);

        for (int i = 0; i < diskSize; i++) {
            DiskResult.Disk disk = diskResult.getDisks().get(i);
            Disk diskDb = Disk.builder()
                    .system(diskResult.getSystem())
                    .currTime(diskResult.getCurrTime())
                    .devName(disk.getDevName())
                    .totalSpace(disk.getTotalSpace())
                    .canUseSpace(disk.getCanUseSpace())
                    .usedSpace(disk.getUsedSpace())
                    .spaceRate(disk.getSpaceRate())
                    .build();
            diskService.insert(diskDb);
        }

    }

    /**
     * 根据磁盘个数构建磁盘编号
     */
    private String builderDisks (List<DiskResult.Disk> disk) {
        StringBuilder diskDev = new StringBuilder();
        for (DiskResult.Disk diskTmp : disk) {
            diskDev.append(diskTmp.getDevName()).append(",");
        }
        return diskDev.toString();
    }
}
