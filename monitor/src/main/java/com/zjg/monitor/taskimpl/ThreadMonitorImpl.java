package com.zjg.monitor.taskimpl;

import com.zjg.monitor.mq.ProducterUtil;
import com.zjg.monitor.response.BaseResult;
import com.zjg.monitor.response.ThreadResult;
import com.zjg.monitor.task.MonitorTask;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author zhangjingao3
 * @Date 2020/3/23 16:25
 */
public class ThreadMonitorImpl implements MonitorTask, Runnable{

    private CountDownLatch countDownLatch;

    public ThreadMonitorImpl(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
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
                List<ThreadInfo> threadInfos = new LinkedList<>();
                for (int i = 1; i <= threadIds.length; i++) {
                    threadInfos.add(ManagementFactory.getThreadMXBean().getThreadInfo(i));
                }
                result.setThreadInfos(threadInfos);
                long[] deadLockThreadIds = ManagementFactory.getThreadMXBean().findDeadlockedThreads();
                if (deadLockThreadIds != null && deadLockThreadIds.length > 0) {
                    List<ThreadInfo> deadLockThreadInfos = new ArrayList<>();
                    for (long id : deadLockThreadIds) {
                        //死锁的堆栈信息
                        deadLockThreadInfos.add(ManagementFactory.getThreadMXBean().getThreadInfo(id, 10));
                    }
                    result.setDeadLockThreadInfo(deadLockThreadInfos);
                }
            }
            result.setCode(BaseResult.CodeEnum.OK.getCode());
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(BaseResult.CodeEnum.ERROR.getCode());
            result.setMsg(e.getMessage());
        }
        ProducterUtil.send(result);
    }

}
