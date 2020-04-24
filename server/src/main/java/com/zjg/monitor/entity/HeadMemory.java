package com.zjg.monitor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zjg
 * <p> 2020/4/2 21:45 </p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HeadMemory implements Serializable {

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
     * 可以获得的最大堆内存
     */
    private double maxMemory;
    /**
     * 初始化堆内存
     */
    private double initMemory;
    /**
     * 已经使用的堆内存
     */
    private double usedMemory;
    /**
     * 堆内存使用率
     */
    private double headMemoryRate;

}
