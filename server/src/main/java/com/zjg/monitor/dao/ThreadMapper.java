package com.zjg.monitor.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjg.monitor.entity.Threads;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ThreadMapper extends BaseMapper<Threads> {

}