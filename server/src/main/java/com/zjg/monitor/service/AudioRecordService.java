package com.zjg.monitor.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zjg.monitor.entity.AudioRecord;

import java.util.List;

/**
 * @author zjg
 * <p> 2020/4/4 12:17 </p>
 */
public interface AudioRecordService {

    AudioRecord selectById(int id);

    boolean insert(AudioRecord audioRecord);

    IPage<AudioRecord> select(int pageNum, int pageSize, String systemName);

    boolean delete(int id);
}
