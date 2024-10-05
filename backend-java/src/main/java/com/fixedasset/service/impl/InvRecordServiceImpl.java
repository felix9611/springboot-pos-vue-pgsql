package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.dto.InvRecordListDto;
import com.fixedasset.entity.InvRecord;
import com.fixedasset.mapper.InvRecordMapper;
import com.fixedasset.service.InvRecordService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class InvRecordServiceImpl extends ServiceImpl<InvRecordMapper, InvRecord> implements InvRecordService {

    @Resource private InvRecordMapper invRecordMapper;

    public void saveRecord(InvRecord invRecord) {
        invRecordMapper.insert(invRecord);
    }

    public Page<InvRecordListDto> newPage(Page page, Wrapper queryWrapper) {
        return invRecordMapper.newPage(page, queryWrapper);
    }

}
