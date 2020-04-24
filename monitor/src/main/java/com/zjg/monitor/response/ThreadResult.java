package com.zjg.monitor.response;

import com.zjg.monitor.util.Config;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.management.ThreadInfo;
import java.util.Date;
import java.util.List;

/**
 * @Author zhangjingao3
 * @Date 2020/3/27 12:43
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ThreadResult extends BaseMessage {

    /**
     * 线程总数
     */
    private int threadCount;
    /**
     * 守护线程数
     */
    private int daemonThreadCount;

    /**
     * 守护线程信息
     */
    private String threadInfos;

    /**
     * 死锁线程
     */
    private int deadLockThreadCount;
    /**
     * 死锁线程信息
     */
    private String deadLockThreadInfo;

    public ThreadResult () {
        this.setSystem(Config.getAppName());
        this.setMsgType(MsgType.THREAD);
        this.setCurrTime(new Date());
    }

}
