package com.zjg.monitor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zjg
 * <p> 2020/4/2 21:46 </p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Memory implements Serializable {


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
     * 内存总量
     */
    private double totalMemory;
    /**
     * 空闲的内存
     */
    private double freeMemory;
    /**
     * 已经使用的内存
     */
    private double usedMemory;
    /**
     * 内存使用率
     */
    private double memoryRate;

}
