package com.zjg.monitor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zjg
 * <p> 2020/4/2 21:30 </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Disk implements Serializable {

    private int id;
    /**
     * 返回的系统名
     */
    private String system;
    /**
     * 当前时间
     */
    private Date currTime;

    /**
     * 盘符名字
     */
    private String devName;
    /**
     * 磁盘总大小
     */
    private double totalSpace;
    /**
     * 可用大小
     */
    private double canUseSpace;
    /**
     * 已经使用的大小
     */
    private double usedSpace;
    /**
     * 空间利用率
     */
    private double spaceRate;

}
