package com.zjg.monitor.response;

import com.zjg.monitor.util.Config;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * @Author zhangjingao3
 * @Date 2020/3/27 11:25
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MemoryResult extends BaseMessage {

    /**
     * 内存总量
     */
    private double totalMemory;
    /**
     * 空闲的内存
     */
    private double freeMemory;
    /**
     * 已经使用的内存
     */
    private double usedMemory;
    /**
     * 内存使用率
     */
    private double memoryRate;

    public MemoryResult () {
        this.setSystem(Config.getAppName());
        this.setMsgType(MsgType.MEMORY);
        this.setCurrTime(new Date());
    }
}
