package com.zjg.monitor.response;

import com.zjg.monitor.util.Config;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @Author zhangjingao3
 * @Date 2020/3/26 14:15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DiskResult extends BaseMessage {

    /**
     * 多个磁盘
     */
    private List<Disk> disks;

    public DiskResult () {
        this.setSystem(Config.getAppName());
        this.setMsgType(MsgType.DISK);
        this.setCurrTime(new Date());
    }

    @Data
    public static class Disk {
        /**
         * 盘符名字
         */
        private String devName;
        /**
         * 磁盘总大小
         */
        private double totalSpace;
        /**
         * 可用大小
         */
        private double canUseSpace;
        /**
         * 已经使用的大小
         */
        private double usedSpace;
        /**
         * 空间利用率
         */
        private double spaceRate;

    }
}
