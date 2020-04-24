package com.zjg.monitor.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zjg
 * <p> 2020/4/3 14:52 </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AudioRecord implements Serializable {

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
     * 报警时间
     */
    private Date audioTime;
    /**
     * 该报警属于哪种监控类型，{@link com.zjg.monitor.response.BaseMessage.MsgType}
     */
    private String audioType;
    /**
     * 发生报警的属性
     */
    private String audioKey;
    /**
     * 报警时的该属性值
     */
    private String nowValue;
    /**
     * 报警时的阈值
     */
    private String thresholdValue;

    public AudioRecord(int systemId, String systemName, Date audioTime, String audioType, String audioKey, String nowValue, String thresholdValue) {
        this.systemId = systemId;
        this.systemName = systemName;
        this.audioTime = audioTime;
        this.audioType = audioType;
        this.audioKey = audioKey;
        this.nowValue = nowValue;
        this.thresholdValue = thresholdValue;
    }


}
