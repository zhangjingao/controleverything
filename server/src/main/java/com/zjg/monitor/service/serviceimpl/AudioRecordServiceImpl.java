package com.zjg.monitor.service.serviceimpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjg.monitor.dao.AudioRecordMapper;
import com.zjg.monitor.entity.AudioRecord;
import com.zjg.monitor.service.AudioRecordService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author zjg
 * <p> 2020/4/4 12:34 </p>
 */
@Service
public class AudioRecordServiceImpl implements AudioRecordService {

    @Resource
    private AudioRecordMapper audioRecordMapper;

    @Override
    public AudioRecord selectById(int id) {
        return audioRecordMapper.selectById(id);
    }

    @Override
    public boolean insert(AudioRecord audioRecord) {
        return audioRecordMapper.insert(audioRecord) == 1;
    }

    @Override
    public IPage<AudioRecord> select(int pageNum, int pageSize, String systemName) {
        Page<AudioRecord> page = new Page<>(pageNum, pageSize);
        QueryWrapper<AudioRecord> queryWrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(systemName)) {
            queryWrapper.like("system_name", systemName);
        }
        return audioRecordMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean delete(int id) {
        return audioRecordMapper.deleteById(id) == 1;
    }
}
