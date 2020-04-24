package com.zjg.monitor.toresult;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zjg
 * <p> 2020/4/19 20:15 </p>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiskChartsResult {


    /**
     * 状态码，200/500
     */
    private Integer code;
    /**
     * 500的原因
     */
    private String msg;

    /**
     * 多个图表
     */
    public List<ECharts> eCharts;

    public DiskChartsResult(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ECharts {

        /**
         * 图表名
         */
        private String chartsName;

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
    }

}
