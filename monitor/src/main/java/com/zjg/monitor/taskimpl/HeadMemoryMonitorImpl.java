package com.zjg.monitor.taskimpl;

import com.zjg.monitor.mq.Producter;
import com.zjg.monitor.response.BaseMessage;
import com.zjg.monitor.response.HeadMemoryResult;
import com.zjg.monitor.task.MonitorTask;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.util.concurrent.CountDownLatch;

/**
 * @Author zhangjingao3
 * @Date 2020/3/23 15:58
 */
public class HeadMemoryMonitorImpl implements MonitorTask, Runnable{

    private CountDownLatch countDownLatch;

    public HeadMemoryMonitorImpl(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        this.monitor();
        countDownLatch.countDown();
    }

    @Override
    public void monitor() {
        HeadMemoryResult result = new HeadMemoryResult();
        try {
            MemoryUsage memoryMXBean = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
            result.setMaxMemory((double) memoryMXBean.getMax() / 1024 / 1024);
            result.setInitMemory((double) memoryMXBean.getInit() / 1024 / 1024);
            result.setUsedMemory((double) memoryMXBean.getUsed() / 1024 / 1024);
            result.setHeadMemoryRate((double) memoryMXBean.getUsed() / memoryMXBean.getMax());
            result.setCode(BaseMessage.CodeEnum.OK);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(BaseMessage.CodeEnum.ERROR);
            result.setMsg(e.getMessage());
        }
        Producter.send(result);
    }


}
