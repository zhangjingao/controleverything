package com.zjg.monitor.response;

import com.zjg.monitor.util.Config;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @Author zhangjingao3
 * @Date 2020/3/26 16:21
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class GcResult extends BaseMessage {

    private List<GC> gcs;
    /**
     * gc总次数
     */
    private long gcCount;

    public GcResult () {
        this.setSystem(Config.getAppName());
        this.setMsgType(MsgType.GC);
        this.setCurrTime(new Date());
    }

    @Data
    public static class GC {
        /**
         * 垃圾收集器名字
         */
        private String gcCollectName;
        /**
         * 作用范围
         */
        private String gcMemoryPoolName;
        /**
         * 垃圾收集器次数
         */
        private long gcCount;
        /**
         * 垃圾收集使用的时间
         */
        private long gcTime;
    }

}
