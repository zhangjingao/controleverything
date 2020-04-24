package com.zjg.monitor.toresult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zjg
 * <p> 2020/4/14 19:38 </p>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChartsResult {

    /**
     * 状态码，200/500
     */
    private Integer code;
    /**
     * 500的原因
     */
    private String msg;

    /**
     * 系列
     */
    private List<String> series;

    /**
     * x坐标数据
     */
    private List<Date> xDatas;

    /**
     * 系列和数据
     */
    private Map<String, List<Double>> yDatas;

    public ChartsResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
