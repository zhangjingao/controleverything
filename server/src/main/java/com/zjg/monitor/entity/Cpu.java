package com.zjg.monitor.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * cpu报警使用的是平均使用率
 * @author zjg
 * <p> 2020/4/2 20:47 </p>
 */
@Data
@NoArgsConstructor
public class Cpu implements Serializable{

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
     * cpu编号
     */
    private int cpuId;
    /**
     * 该编号的cpu使用率
     */
    private Double cpuRate;

    public Cpu(String system, Date currTime, int cpuId, Double cpuRate) {
        this.system = system;
        this.currTime = currTime;
        this.cpuId = cpuId;
        this.cpuRate = cpuRate;
    }
}
