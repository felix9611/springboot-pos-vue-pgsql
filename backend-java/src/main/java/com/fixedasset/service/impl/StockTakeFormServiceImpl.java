package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.dto.StockTakeFormListDto;
import com.fixedasset.entity.ActionRecord;
import com.fixedasset.entity.StockTakeForm;
import com.fixedasset.mapper.ActionRecordMapper;
import com.fixedasset.mapper.StockTakeFormMapper;
import com.fixedasset.service.ActionRecordService;
import com.fixedasset.service.StockTakeFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.OffsetDateTime;

@Service
public class StockTakeFormServiceImpl extends ServiceImpl<StockTakeFormMapper, StockTakeForm> implements StockTakeFormService {

    @Resource private StockTakeFormMapper stockTakeFormMapper;

    @Resource private ActionRecordService actionRecordService;

    @Resource private StockTakeForm stockTakeForm;

    public void createStockTake(StockTakeForm stockTakeForm) {
        LambdaQueryWrapper<StockTakeForm> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(StockTakeForm::getStatus, 0);
        queryWrapper.eq(StockTakeForm::getName, stockTakeForm.getName());
        StockTakeForm checkOne = stockTakeFormMapper.selectOne(queryWrapper);

        if (checkOne == null) {
            stockTakeForm.setStartTime(OffsetDateTime.now());
            stockTakeForm.setStatus(1);
            stockTakeFormMapper.insert(stockTakeForm);

            actionRecordService.createdAction(
                "Create", 
                "POST", 
                "Stock Take", 
                stockTakeForm.toString(), 
                "Success"
            );
        } else {
            actionRecordService.createdAction(
                "Create", 
                "POST", 
                "Stock Take",  
                stockTakeForm.toString(), 
                "Failure"
            );
            throw new RuntimeException("Exist in records!");
        }
    }

    public void voidForm(Long id) {
        LambdaQueryWrapper<StockTakeForm> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(StockTakeForm::getStatus, 0);
        queryWrapper.eq(StockTakeForm::getId, id);
        StockTakeForm checkOne = stockTakeFormMapper.selectOne(queryWrapper);

        if (checkOne.equals(id)) {
            stockTakeForm.setId(id);
            stockTakeForm.setEndTime(OffsetDateTime.now());
            stockTakeForm.setStatus(0);
            stockTakeFormMapper.updateById(stockTakeForm);

            actionRecordService.createdAction(
                "Void", 
                "Delete",
                "Stock Take", 
                stockTakeForm.toString(), 
                "Success"
            );
        } else {
            actionRecordService.createdAction(
                "Void", 
                "Delete", 
                "Stock Take", 
                stockTakeForm.toString(), 
                "Failure"
            );
            throw new RuntimeException("No active data in records!");
        }
    }

    public void finishForm(Long id) {
        LambdaQueryWrapper<StockTakeForm> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(StockTakeForm::getStatus, 0);
        queryWrapper.eq(StockTakeForm::getId, id);
        StockTakeForm checkOne = stockTakeFormMapper.selectOne(queryWrapper);


        if (checkOne.equals(id)) {
            stockTakeForm.setId(id);
            stockTakeForm.setEndTime(OffsetDateTime.now());
            stockTakeForm.setStatus(2);
            stockTakeFormMapper.updateById(stockTakeForm);

            actionRecordService.createdAction(
                "Finish Stock Take", 
                "Delete", 
                "Stock Take", 
                stockTakeForm.toString(), 
                "Success"
            );
        } else {
            actionRecordService.createdAction(
                "Finish Stock Take", 
                "Delete", 
                "Stock Take", 
                stockTakeForm.toString(), 
                "Failure"
            );
            throw new RuntimeException("No active data in records!");
        }
    }

    public StockTakeForm getFrom(Long id) {
        return stockTakeFormMapper.selectById(id);
    }

    public Page<StockTakeFormListDto> newPage(Page page, Wrapper queryWrapper) {
        return stockTakeFormMapper.page(page, queryWrapper);
    }
}
