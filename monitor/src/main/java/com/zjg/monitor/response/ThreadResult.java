package com.zjg.monitor.response;

import com.zjg.monitor.util.Config;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.lang.management.ThreadInfo;
import java.util.List;

/**
 * @Author zhangjingao3
 * @Date 2020/3/27 12:43
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ThreadResult extends BaseResult {

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
    private List<ThreadInfo> threadInfos;

    /**
     * 死锁线程
     */
    private int deadLockThreadCount;
    /**
     * 死锁线程信息
     */
    private List<ThreadInfo> deadLockThreadInfo;

    public ThreadResult () {
        this.setSystem(Config.getAppName());
        this.setMsgType(MsgType.THREAD);
    }

}
