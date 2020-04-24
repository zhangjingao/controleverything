package com.zjg.monitor.taskimpl;

import com.zjg.monitor.mq.Producter;
import com.zjg.monitor.response.BaseMessage;
import com.zjg.monitor.response.CpuResult;
import com.zjg.monitor.task.MonitorTask;
import lombok.extern.slf4j.Slf4j;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 监控cpu
 * @Author zhangjingao3
 * @Date 2020/3/15 16:14
 */
@Slf4j
public class CpuMonitorImpl implements MonitorTask, Runnable{

    private CountDownLatch countDownLatch;

    public CpuMonitorImpl(CountDownLatch countDownLatch) {
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
        CpuResult cpuResult = new CpuResult();
        List<String> cpuRates = new ArrayList<>();
        try {
            CpuPerc[] cpuPercs = sigar.getCpuPercList();
            for (CpuPerc cpuPerc : cpuPercs) {
                log.info("cpu总的使用率：{}", CpuPerc.format(cpuPerc.getCombined()));
                cpuRates.add(CpuPerc.format(cpuPerc.getCombined()));
            }
            cpuResult.setCode(BaseMessage.CodeEnum.OK);
        } catch (SigarException e) {
            e.printStackTrace();
            cpuResult.setCode(BaseMessage.CodeEnum.ERROR);
            cpuResult.setMsg(e.getMessage());
        } finally {
            sigar.close();
        }
        cpuResult.setCpuRates(cpuRates);
        Producter.send(cpuResult);
    }

    public static void main(String[] args) {
        new CpuMonitorImpl(new CountDownLatch(1)).monitor();
    }

}
