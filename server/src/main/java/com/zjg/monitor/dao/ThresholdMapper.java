package com.zjg.monitor.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjg.monitor.entity.Threshold;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ThresholdMapper extends BaseMapper<Threshold> {
}