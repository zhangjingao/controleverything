package com.zjg.monitor.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * 无数据处理请求处理，如跳转链接
 * @author zjg
 * <p> 2020/4/7 20:18 </p>
 */
@Slf4j
@RestController
@RequestMapping("req")
public class NoDataRequestController {


    @RequestMapping("{path}")
    public ModelAndView req (@PathVariable String path) {
        return new ModelAndView(path);
    }

}
