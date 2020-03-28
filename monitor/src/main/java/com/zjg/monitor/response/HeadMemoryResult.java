package com.zjg.monitor.response;

import com.zjg.monitor.util.Config;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author zhangjingao3
 * @Date 2020/3/26 19:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class HeadMemoryResult extends BaseResult {

    /**
     * 可以获得的最大堆内存
     */
    private double maxMemory;
    /**
     * 初始化堆内存
     */
    private double initMemory;
    /**
     * 已经使用的堆内存
     */
    private double usedMemory;
    /**
     * 堆内存使用率
     */
    private double headMemoryRate;

    public HeadMemoryResult () {
        this.setSystem(Config.getAppName());
        this.setMsgType(MsgType.HEAD_MEMORY);
    }

}
