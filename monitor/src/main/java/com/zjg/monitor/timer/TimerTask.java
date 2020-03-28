package com.zjg.monitor.timer;

import com.zjg.monitor.taskimpl.*;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * @Author zhangjingao3
 * @Date 2020/3/27 18:12
 */
@Slf4j
public class TimerTask extends java.util.TimerTask {

    private ThreadPoolExecutor poolExecutor;

    public TimerTask () {
        poolExecutor = new ThreadPoolExecutor(8, 8, 2, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>());
    }

    @Override
    public void run() {
        log.error("start to do task");
        CountDownLatch countDownLatch = new CountDownLatch(6);
        poolExecutor.execute(new CpuMonitorImpl(countDownLatch));
        poolExecutor.execute(new DiskMonitorImpl(countDownLatch));
        poolExecutor.execute(new GCMonitorImpl(countDownLatch));
        poolExecutor.execute(new HeadMemoryMonitorImpl(countDownLatch));
        poolExecutor.execute(new MemoryMonitorImpl(countDownLatch));
        poolExecutor.execute(new ThreadMonitorImpl(countDownLatch));
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("TimerTask.run error", e);
        }
        log.error("task end");
    }

}
