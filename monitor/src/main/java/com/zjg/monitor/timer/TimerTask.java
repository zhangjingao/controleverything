package com.zjg.monitor.timer;

import com.zjg.monitor.taskimpl.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import java.util.concurrent.*;

/**
 * @Author zhangjingao3
 * @Date 2020/3/27 18:12
 */
@Slf4j
@Data
public class TimerTask {

    /**
     * 线程池
     */
    private ThreadPoolExecutor poolExecutor;
    /**
     * 是否监控线程信息
     */
    private boolean startThreadInfo;
    /**
     * 是否开启全部监控，如果为否则只开启cpu，磁盘，内存三个监控
     */
    private boolean startAllMonitor;

    public TimerTask () {
        startAllMonitor = true;
        startThreadInfo = false;
        poolExecutor = new ThreadPoolExecutor(6, 6, 2, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>());
    }

    public TimerTask(boolean startThreadInfo) {
        startAllMonitor = true;
        this.startThreadInfo = startThreadInfo;
        poolExecutor = new ThreadPoolExecutor(6, 6, 2, TimeUnit.MINUTES,
                new LinkedBlockingQueue<>());
    }

    public TimerTask(boolean startThreadInfo, ThreadPoolExecutor executor) {
        startAllMonitor = true;
        this.startThreadInfo = startThreadInfo;
        poolExecutor = executor;
    }

    public TimerTask(boolean startThreadInfo, ThreadPoolExecutor poolExecutor, boolean startAllMonitor) {
        this.poolExecutor = poolExecutor;
        this.startThreadInfo = startThreadInfo;
        this.startAllMonitor = startAllMonitor;
    }

    public void monitor() {
        log.error("start to do task");
        CountDownLatch countDownLatch;
        if (!startAllMonitor) {
            countDownLatch = new CountDownLatch(3);
        } else {
            countDownLatch = new CountDownLatch(6);
        }
        poolExecutor.execute(new CpuMonitorImpl(countDownLatch));
        poolExecutor.execute(new DiskMonitorImpl(countDownLatch));
        poolExecutor.execute(new MemoryMonitorImpl(countDownLatch));
        if (startAllMonitor) {
            poolExecutor.execute(new HeadMemoryMonitorImpl(countDownLatch));
            poolExecutor.execute(new GCMonitorImpl(countDownLatch));
            poolExecutor.execute(new ThreadMonitorImpl(countDownLatch, startThreadInfo));
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("TimerTask.run error", e);
        }
        log.error("task end");
    }

}
