package com.zjg.monitor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.management.ThreadInfo;
import java.util.Date;
import java.util.List;

/**
 * @author zjg
 * <p> 2020/4/2 21:47 </p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Threads implements Serializable {

    private int id;
    /**
     * 返回的系统名
     */
    private String system;
    /**
     * 当前时间
     */
    private Date currTime;


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


}
