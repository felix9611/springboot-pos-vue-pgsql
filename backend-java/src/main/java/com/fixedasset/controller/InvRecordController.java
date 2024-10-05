package com.fixedasset.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.common.lang.Result;
import com.fixedasset.dto.InvRecordListDto;
import com.fixedasset.entity.InvRecord;
import com.fixedasset.service.InvRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/inv/record")
public class InvRecordController extends BaseController{
    @Resource private InvRecordService invRecordService;

    @PostMapping("/list")
    public Result listAll(@RequestBody InvRecord invRecord) {
        Page page = new Page(invRecord.getPage(), invRecord.getLimit());
        LambdaQueryWrapper<InvRecord> queryWrapper = Wrappers.lambdaQuery();

        if (!(invRecord.getTimeAtFrom()==null) && !(invRecord.getTimeAtTo()==null)) {
            queryWrapper.between(InvRecord::getTimeAt, invRecord.getTimeAtFrom(), invRecord.getTimeAtTo());
        }

        Page<InvRecordListDto> iPage = invRecordService.newPage(page, queryWrapper);

        return Result.succ(iPage);
    }
}
