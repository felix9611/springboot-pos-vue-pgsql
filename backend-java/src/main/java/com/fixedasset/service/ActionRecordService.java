package com.fixedasset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixedasset.entity.ActionRecord;

public interface ActionRecordService extends IService<ActionRecord> {

    void createdAction(String name, String method, String from, String data, String success);

}
