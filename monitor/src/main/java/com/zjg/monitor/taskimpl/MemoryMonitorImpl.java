package com.zjg.monitor.taskimpl;

import com.zjg.monitor.mq.Producter;
import com.zjg.monitor.response.BaseMessage;
import com.zjg.monitor.response.MemoryResult;
import com.zjg.monitor.task.MonitorTask;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.concurrent.CountDownLatch;

/**
 * @Author zhangjingao3
 * @Date 2020/3/15 23:07
 */
public class MemoryMonitorImpl implements MonitorTask, Runnable{

    private CountDownLatch countDownLatch;

    public MemoryMonitorImpl(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        this.monitor();
        countDownLatch.countDown();
    }


    @Override
    public void monitor() {
        Sigar sigar = new Sigar();
        MemoryResult memoryResult = new MemoryResult();
        try {
            Mem mem = sigar.getMem();
            memoryResult.setTotalMemory((double) mem.getTotal() / 1024 / 1024 / 1024);
            memoryResult.setUsedMemory((double) mem.getUsed() / 1024L / 1024L / 1024L);
            memoryResult.setFreeMemory((double) mem.getFree() / 1024L / 1024L / 1024L);
            memoryResult.setMemoryRate(mem.getUsedPercent());
            memoryResult.setCode(BaseMessage.CodeEnum.OK);
        } catch (SigarException e) {
            e.printStackTrace();
            memoryResult.setCode(BaseMessage.CodeEnum.ERROR);
            memoryResult.setMsg(e.getMessage());
        }
        Producter.send(memoryResult);
    }


}
