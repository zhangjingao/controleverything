package com.zjg.monitor.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjg.monitor.dao.SystemMapper;
import com.zjg.monitor.entity.Systems;
import com.zjg.monitor.service.SystemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zjg
 * <p> 2020/4/3 15:46 </p>
 */
@Slf4j
@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    private SystemMapper systemMapper;

    @Override
    public IPage<Systems> select(int pageNum, int pageSize,String systemName) {
        Page<Systems> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Systems> queryWrapper = null;
        if (!StringUtils.isEmpty(systemName)) {
            queryWrapper = new QueryWrapper<>();
            queryWrapper.like("system_name", systemName);
        }
        return systemMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean updateSystem(Integer id, String emails, boolean stopAudio) {
        Systems systems = new Systems(id, emails, stopAudio);
        return systemMapper.updateById(systems) == 1;
    }

    @Override
    public Systems select(int id) {
        return systemMapper.selectById(id);
    }

    @Override
    public void insert(Systems systems) {
        systemMapper.insert(systems);
    }

    @Override
    public void addAudioCount(Systems systems) {
        int count = systems.getAudioCount();
        UpdateWrapper<Systems> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("audioCount", count + 1);
        systemMapper.update(systems, updateWrapper);
    }

    @Override
    public List<Systems> selectAll() {
        return systemMapper.selectList(null);
    }

    @Override
    public Systems selectBySystemName(String system) {
        QueryWrapper<Systems> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("system_name", system);
        return systemMapper.selectOne(queryWrapper);
    }


}
