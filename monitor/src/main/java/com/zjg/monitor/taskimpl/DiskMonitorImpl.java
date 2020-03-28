package com.zjg.monitor.taskimpl;

import com.zjg.monitor.mq.ProducterUtil;
import com.zjg.monitor.response.BaseResult;
import com.zjg.monitor.response.DiskResult;
import com.zjg.monitor.task.MonitorTask;
import com.zjg.monitor.util.Contant;
import lombok.extern.slf4j.Slf4j;
import org.hyperic.sigar.FileSystem;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 磁盘监控
 * @Author zhangjingao3
 * @Date 2020/3/15 23:29
 */
@Slf4j
public class DiskMonitorImpl implements MonitorTask, Runnable{

    private CountDownLatch countDownLatch;

    public DiskMonitorImpl(CountDownLatch countDownLatch) {
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
        DiskResult diskResult = new DiskResult();
        List<DiskResult.Disk> diskList = new ArrayList<>();
        try {
            FileSystem[] fileSystems = sigar.getFileSystemList();
            for (FileSystem fileSystem : fileSystems) {
                DiskResult.Disk disk = new DiskResult.Disk();
                disk.setDevName(fileSystem.getDevName());
                FileSystemUsage fileSystemUsage = sigar.getFileSystemUsage(fileSystem.getDirName());
                if (fileSystem.getType() == 2) {
                    // 文件系统总大小
                    disk.setTotalSpace((double) fileSystemUsage.getTotal() / 1024 / 1024);
                    // 文件系统可用大小
                    disk.setCanUseSpace((double) fileSystemUsage.getAvail() / 1024 / 1024);
                    // 文件系统已经使用量
                    disk.setUsedSpace((double) fileSystemUsage.getUsed() / 1024 / 1024);
                    double usePercent = fileSystemUsage.getUsePercent() * 100D;
                    // 文件系统资源的利用率
                    disk.setSpaceRate(usePercent);
                }
                diskList.add(disk);
            }
            diskResult.setCode(BaseResult.CodeEnum.OK.getCode());
        } catch (SigarException e) {
            e.printStackTrace();
            diskResult.setCode(BaseResult.CodeEnum.ERROR.getCode());
            diskResult.setMsg(e.getMessage());
        } finally {
            sigar.close();
        }
        diskResult.setDisks(diskList);
        ProducterUtil.send(diskResult);
    }

}
