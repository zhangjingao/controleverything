package com.zjg.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjg.monitor.entity.Systems;

import java.util.List;

/**
 * @author zjg
 * <p> 2020/4/3 15:45 </p>
 */
public interface SystemService {

    /**
     * 查找系统
     */
    IPage<Systems> select(int pageNum, int pageSize, String systemName);

    /**
     * 修改系统信息
     */
    boolean updateSystem(Integer id, String emails, boolean stopAudio);

    Systems select(int id);

    Systems selectBySystemName(String system);

    void insert(Systems systems);

    void addAudioCount(Systems systems);

    List<Systems> selectAll();
}
