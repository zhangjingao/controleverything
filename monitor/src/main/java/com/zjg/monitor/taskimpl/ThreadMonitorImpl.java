package com.zjg.monitor.taskimpl;

import com.zjg.monitor.mq.Producter;
import com.zjg.monitor.response.BaseMessage;
import com.zjg.monitor.response.ThreadResult;
import com.zjg.monitor.task.MonitorTask;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author zhangjingao3
 * @Date 2020/3/23 16:25
 */
public class ThreadMonitorImpl implements MonitorTask, Runnable{

    private CountDownLatch countDownLatch;
    private boolean startThreadInfo;

    public ThreadMonitorImpl(CountDownLatch countDownLatch, boolean startThreadInfo) {
        this.countDownLatch = countDownLatch;
        this.startThreadInfo = startThreadInfo;
    }

    @Override
    public void run() {
        this.monitor();
        countDownLatch.countDown();
    }

    @Override
    public void monitor() {
        ThreadResult result = new ThreadResult();
        try {
            result.setThreadCount(ManagementFactory.getThreadMXBean().getThreadCount());
            result.setDaemonThreadCount(ManagementFactory.getThreadMXBean().getDaemonThreadCount());
            long[] threadIds = ManagementFactory.getThreadMXBean().getAllThreadIds();
            if (threadIds != null && threadIds.length > 0) {
                if (startThreadInfo) {
                    StringBuilder threadInfos = new StringBuilder();
                    for (int i = 1; i <= threadIds.length; i++) {
                        threadInfos.append(ManagementFactory.getThreadMXBean().getThreadInfo(i, 1));
                    }
                    result.setThreadInfos(threadInfos.toString());
                }
                long[] deadLockThreadIds = ManagementFactory.getThreadMXBean().findDeadlockedThreads();
                if (deadLockThreadIds != null && deadLockThreadIds.length > 0) {
                    StringBuilder deadLockThreadInfos = new StringBuilder();
                    for (long id : deadLockThreadIds) {
                        //死锁的堆栈信息
                        deadLockThreadInfos.append(ManagementFactory.getThreadMXBean().getThreadInfo(id, 10).toString());
                    }
                    result.setDeadLockThreadInfo(deadLockThreadInfos.toString());
                }
            }
            result.setCode(BaseMessage.CodeEnum.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(BaseMessage.CodeEnum.ERROR);
            result.setMsg(e.getMessage());
        }
        Producter.send(result);
    }

}
