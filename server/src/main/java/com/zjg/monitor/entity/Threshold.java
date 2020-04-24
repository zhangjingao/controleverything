package com.zjg.monitor.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zjg
 * <p> 2020/4/3 13:11 </p>
 */
@Data
public class Threshold implements Serializable{

    private int id;
    /**
     * 属于哪个系统id
     */
    private int systemId;
    /**
     * 系统名
     */
    private String systemName;

    /**
     * 该阈值属于哪种监控类型，{@link com.zjg.monitor.response.BaseMessage.MsgType}
     */
    private String monitorType;
    /**
     * 该阈值属于该类型下的哪一个属性
     */
    private String monitorKey;
    /**
     * 大于该值时报警
     */
    private double threshold;

    public Threshold () {}

    public Threshold(int systemId, String systemName, String monitorType, String monitorKey, double threshold) {
        this.systemId = systemId;
        this.systemName = systemName;
        this.monitorType = monitorType;
        this.monitorKey = monitorKey;
        this.threshold = threshold;
    }

    public Threshold(int id, double thresholdValue) {
        this.id = id;
        this.threshold = thresholdValue;
    }
}
