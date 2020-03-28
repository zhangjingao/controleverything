package com.zjg.monitor;

/**
 * @Author zhangjingao3
 * @Date 2020/3/15 16:12
 */
public class Main {

    public static void main(String[] args) {

        /*Boolean boo = false;
        Integer boo2 = 1;
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (boo) {
                    System.out.println("ok 1");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (boo2) {}
                    System.out.println("end 1");
                }
            }
        }, "圣诞节哦十九点三").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (boo2) {
                    System.out.println("ok 2");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (boo){}
                    System.out.println("end 2");
                }
            }
        }, "说的就是但是").start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
/*
        Sigar sigar = new Sigar();
        try {
            CpuPerc[] cpuPercs = sigar.getCpuPercList();
            for (CpuPerc cpuPerc : cpuPercs) {
                System.out.println("   cpu总的使用率：" + CpuPerc.format(cpuPerc.getCombined()));
            }
        } catch (SigarException e) {
            e.printStackTrace();
        }

        try {
            Mem mem = sigar.getMem();
            System.out.println("内存总量：" + (double) mem.getTotal() / 1024 / 1024 / 1024 +"G");
            System.out.println("已使用的：" + (double) mem.getUsed() / 1024L / 1024L / 1024L +"G");
            System.out.println("空闲的：" + (double) mem.getFree() / 1024L / 1024L / 1024L +"G");
        } catch (SigarException e) {
            e.printStackTrace();
        }

        try {
            FileSystem[] fileSystems = sigar.getFileSystemList();
            for (FileSystem fileSystem : fileSystems) {
                System.out.println("盘符：" + fileSystem.getDevName());
                FileSystemUsage fileSystemUsage = sigar.getFileSystemUsage(fileSystem.getDirName());
                if (fileSystem.getType() == 2) {
                    // 文件系统总大小
                    System.out.println(fileSystem.getDevName() + "总大小:    " + (double) fileSystemUsage.getTotal() / 1024 / 1024 + "G");
                    // 文件系统剩余大小
                    System.out.println(fileSystem.getDevName() + "剩余大小:    " + (double) fileSystemUsage.getFree() / 1024 / 1024 + "G");
                    // 文件系统可用大小
                    System.out.println(fileSystem.getDevName() + "可用大小:    " + (double) fileSystemUsage.getAvail() / 1024 / 1024 + "G");
                    // 文件系统已经使用量
                    System.out.println(fileSystem.getDevName() + "已经使用量:    " + (double) fileSystemUsage.getUsed() / 1024 / 1024 + "G");
                    double usePercent = fileSystemUsage.getUsePercent() * 100D;
                    // 文件系统资源的利用率
                    System.out.println(fileSystem.getDevName() + "资源的利用率:    " + usePercent + "%");
                }
            }
        } catch (SigarException e) {
            e.printStackTrace();
        }*/
    }

}
