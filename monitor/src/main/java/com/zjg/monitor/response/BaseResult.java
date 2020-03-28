package com.zjg.monitor.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author zhangjingao3
 * @Date 2020/3/25 17:51
 */
@Data
public class BaseResult {

    /**
     * 状态码
     */
    private int code;
    /**
     * 错误信息
     */
    private String msg;
    /**
     * 返回的系统名
     */
    private String system;
    /**
     * 信息类型
     */
    private MsgType msgType;

    private Object data;


    public enum CodeEnum {
        OK(200), ERROR(500);

        @Getter
        @Setter
        private int code;

        CodeEnum(int code) {
            this.code = code;
        }
    }

    /**
     * 这个消息属于监控的哪一种
     */
    public enum MsgType {

        CPU, DISK, GC, HEAD_MEMORY, THREAD, MEMORY

    }

}
