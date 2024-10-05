package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.dto.StockTakeFormListDto;
import com.fixedasset.entity.ActionRecord;
import com.fixedasset.entity.StockTakeForm;
import com.fixedasset.mapper.ActionRecordMapper;
import com.fixedasset.mapper.StockTakeFormMapper;
import com.fixedasset.service.StockTakeFormService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
public class StockTakeFormServiceImpl extends ServiceImpl<StockTakeFormMapper, StockTakeForm> implements StockTakeFormService {

    @Resource private StockTakeFormMapper stockTakeFormMapper;
    @Resource private ActionRecordMapper actionRecordMapper;
    @Resource private ActionRecord actionRecord;
    @Resource private StockTakeForm stockTakeForm;

    public void createStockTake(StockTakeForm stockTakeForm) {
        stockTakeForm.setStartTime(OffsetDateTime.now());
        stockTakeForm.setStatus(1);
        stockTakeFormMapper.insert(stockTakeForm);

        actionRecord.setActionName("Create");
        actionRecord.setActionMethod("POST");
        actionRecord.setActionFrom("Stock Take");
        actionRecord.setActionData(stockTakeForm.toString());
        actionRecord.setActionSuccess("Success");
        actionRecord.setCreated(OffsetDateTime.now());
        this.createdAction(actionRecord);
    }

    public void voidForm(Long id) {
        stockTakeForm.setId(id);
        stockTakeForm.setEndTime(OffsetDateTime.now());
        stockTakeForm.setStatus(0);
        stockTakeFormMapper.updateById(stockTakeForm);

        actionRecord.setActionName("Finish");
        actionRecord.setActionMethod("REMOVE");
        actionRecord.setActionFrom("Stock Take");
        actionRecord.setActionData(stockTakeForm.toString());
        actionRecord.setActionSuccess("Success");
        actionRecord.setCreated(OffsetDateTime.now());
        this.createdAction(actionRecord);

    }

    public void finishForm(Long id) {
        stockTakeForm.setId(id);
        stockTakeForm.setEndTime(OffsetDateTime.now());
        stockTakeForm.setStatus(2);
        stockTakeFormMapper.updateById(stockTakeForm);

        actionRecord.setActionName("Finish");
        actionRecord.setActionMethod("REMOVE");
        actionRecord.setActionFrom("Stock Take");
        actionRecord.setActionData(stockTakeForm.toString());
        actionRecord.setActionSuccess("Success");
        actionRecord.setCreated(OffsetDateTime.now());
        this.createdAction(actionRecord);

    }

    public StockTakeForm getFrom(Long id) {
        return stockTakeFormMapper.selectById(id);
    }

    public Page<StockTakeFormListDto> newPage(Page page, Wrapper queryWrapper) {
        return stockTakeFormMapper.page(page, queryWrapper);
    }

    public int createdAction(ActionRecord actionRecord) {
        return actionRecordMapper.insert(actionRecord);
    }

}
