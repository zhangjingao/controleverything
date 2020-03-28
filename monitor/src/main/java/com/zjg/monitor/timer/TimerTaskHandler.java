package com.zjg.monitor.timer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;

/**
 * @Author zhangjingao3
 * @Date 2020/3/27 18:09
 */
public class TimerTaskHandler {

    public static void main(String[] args) {
        System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        Timer timer = new Timer(true);
        TimerTask timerTask = new TimerTask();
        timer.schedule(timerTask, 60000, 60000);
    }

}
