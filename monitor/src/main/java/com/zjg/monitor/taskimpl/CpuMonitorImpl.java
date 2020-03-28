package com.zjg.monitor.taskimpl;

import com.zjg.monitor.mq.ProducterUtil;
import com.zjg.monitor.response.BaseResult;
import com.zjg.monitor.response.CpuResult;
import com.zjg.monitor.task.MonitorTask;
import lombok.extern.slf4j.Slf4j;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

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
        List<Double> cpuRates = new ArrayList<>();
        try {
            CpuPerc[] cpuPercs = sigar.getCpuPercList();
            for (CpuPerc cpuPerc : cpuPercs) {
                log.info("cpu总的使用率：{}", CpuPerc.format(cpuPerc.getCombined()));
                cpuRates.add(Double.valueOf(CpuPerc.format(cpuPerc.getCombined())));
            }
            cpuResult.setCode(BaseResult.CodeEnum.OK.getCode());
        } catch (SigarException e) {
            e.printStackTrace();
            cpuResult.setCode(BaseResult.CodeEnum.ERROR.getCode());
            cpuResult.setMsg(e.getMessage());
        } finally {
            sigar.close();
        }
        cpuResult.setCpuRates(cpuRates);
        ProducterUtil.send(cpuResult);
    }

}
