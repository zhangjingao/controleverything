package com.zjg.monitor.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author zjg
 * <p> 2020/4/3 13:07 </p>
 */
@Data
public class Systems implements Serializable {

    private int id;

    /**
     * 系统名
     */
    private String systemName;
    /**
     * 是否关闭报警，默认false
     */
    private boolean stopAudio;

    /**
     * 该系统的邮件报警人，多个之间使用，隔开
     */
    private String emails;
    /**
     * 该系统报警总次数
     */
    private int audioCount;

    public Systems (){}

    public Systems(String systemName, String emails) {
        this.systemName = systemName;
        this.emails = emails;
    }

    public Systems(int id, String systemName, String emails) {
        this.id = id;
        this.systemName = systemName;
        this.emails = emails;
    }

    public Systems(int id, String emails, boolean stopAudio) {
        this.id = id;
        this.stopAudio = stopAudio;
        this.emails = emails;
    }
}
