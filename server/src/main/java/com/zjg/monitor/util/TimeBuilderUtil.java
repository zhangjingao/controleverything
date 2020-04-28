package com.zjg.monitor.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author zjg
 * <p> 2020/4/17 15:26 </p>
 */
@Slf4j
public class TimeBuilderUtil {

    public static Date[] timeFormater (String timeRange) throws ParseException {
        String[] time = timeRange.split("~");
        String startTimeStr = time[0].trim();
        String stopTimeStr = time[1].trim();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startTime = dateFormat.parse(startTimeStr);
        Date stopTime = dateFormat.parse(stopTimeStr);
        return new Date[]{startTime, stopTime};
    }

    /**
     * * 根据时间获得cpu信息
     * 180个刻度为最大观看频率
     * 3个小时为界，3小时以内取分钟，3小时以上半小时取一个点
     * 3天为界，3天内半小时取一个点，大于3天取半天
     * 最大观看也就是3个月
     */
    public static List<Date> buildTime(Date startTime, Date stopTime) {
        log.error("时间： "+startTime +" " + stopTime);
        long timeMillis = stopTime.getTime() - startTime.getTime();
        List<Date> dates = new LinkedList<>();
        //相差在180分钟内
        if (timeMillis / 1000 / 60 <= 180) {
            while (startTime.before(stopTime)) {
                dates.add(new Date(startTime.getTime()));
                startTime.setTime(startTime.getTime() + 1000 * 60);
            }
        } else if (timeMillis / 1000 / 60 / 60 / 24 <= 3) {
            while (startTime.before(stopTime)) {
                dates.add(new Date(startTime.getTime()));
                startTime.setTime(startTime.getTime() + 1000 * 60 * 30);
            }
        } else {
            while (startTime.before(stopTime)) {
                dates.add(new Date(startTime.getTime()));
                startTime.setTime(startTime.getTime() + 1000 * 60 * 60 * 12);
            }
        }
        if (!dates.contains(stopTime)) {
            dates.add(stopTime);
        }
        return dates;
    }

}
