package com.fixedasset.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fixedasset.dto.StockTakeFormListDto;
import com.fixedasset.entity.StockTakeForm;

public interface StockTakeFormService extends IService<StockTakeForm> {
    void createStockTake(StockTakeForm stockTakeForm);
    void voidForm(Long id);
    void finishForm(Long id);
    Page<StockTakeFormListDto> newPage(Page page, Wrapper queryWrapper);
    StockTakeForm getFrom(Long id);
}
