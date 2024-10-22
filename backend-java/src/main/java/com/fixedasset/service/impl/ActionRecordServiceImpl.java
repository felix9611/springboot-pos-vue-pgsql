package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.entity.ActionRecord;
import com.fixedasset.mapper.ActionRecordMapper;
import com.fixedasset.service.ActionRecordService;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import java.time.OffsetDateTime;

@Service
public class ActionRecordServiceImpl extends ServiceImpl<ActionRecordMapper, ActionRecord> implements ActionRecordService {

    @Resource ActionRecord actionRecord;

    @Resource ActionRecordMapper actionRecordMapper;

    public void createdAction(String name, String method, String from, String data, String success) {
        actionRecord.setActionName(name);
        actionRecord.setActionMethod(method);
        actionRecord.setActionFrom(from);
        actionRecord.setActionData(data);
        actionRecord.setActionSuccess(success);
        actionRecord.setCreated(OffsetDateTime.now());

        actionRecordMapper.insert(actionRecord);
    }

}
