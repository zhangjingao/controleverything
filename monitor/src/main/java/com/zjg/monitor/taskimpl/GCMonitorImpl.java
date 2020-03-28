package com.zjg.monitor.taskimpl;

import com.zjg.monitor.mq.ProducterUtil;
import com.zjg.monitor.response.BaseResult;
import com.zjg.monitor.response.GcResult;
import com.zjg.monitor.task.MonitorTask;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author zhangjingao3
 * @Date 2020/3/23 17:45
 */
public class GCMonitorImpl implements MonitorTask, Runnable{

    private CountDownLatch countDownLatch;

    public GCMonitorImpl(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        this.monitor();
        countDownLatch.countDown();
    }

    @Override
    public void monitor() {
        GcResult gcResult = new GcResult();
        List<GcResult.GC> gcs = new ArrayList<>();
        long gcTotalCount = 0;
        try {
            List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
            for (GarbageCollectorMXBean garbageCollectorMXBean : garbageCollectorMXBeans) {
                GcResult.GC gc = new GcResult.GC();
                gc.setGcCollectName(garbageCollectorMXBean.getName());
                gc.setGcMemoryPoolName(Arrays.toString(garbageCollectorMXBean.getMemoryPoolNames()));
                gc.setGcTime(garbageCollectorMXBean.getCollectionTime());
                long gcCount = garbageCollectorMXBean.getCollectionCount();
                gc.setGcCount(gcCount);
                gcTotalCount += gcCount;
                gcs.add(gc);
            }
            gcResult.setGcCount(gcTotalCount);
            gcResult.setCode(BaseResult.CodeEnum.OK.getCode());
            gcResult.setGcs(gcs);
        } catch (Exception e) {
            e.printStackTrace();
            gcResult.setCode(BaseResult.CodeEnum.ERROR.getCode());
            gcResult.setMsg(e.getMessage());
        }
        ProducterUtil.send(gcResult);
    }

}
