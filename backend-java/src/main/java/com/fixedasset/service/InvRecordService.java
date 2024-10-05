package com.fixedasset.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fixedasset.dto.InvRecordListDto;
import com.fixedasset.entity.InvRecord;

public interface InvRecordService extends IService<InvRecord> {
    void saveRecord(InvRecord invRecord);
    Page<InvRecordListDto> newPage(Page page, Wrapper queryWrapper);
}
