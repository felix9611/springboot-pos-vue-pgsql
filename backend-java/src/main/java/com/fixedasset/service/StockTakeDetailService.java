package com.fixedasset.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fixedasset.dto.StockTakeItemListDto;
import com.fixedasset.entity.StockTakeDetail;

public interface StockTakeDetailService extends IService<StockTakeDetail> {
    void stockTakeItem(StockTakeDetail stockTakeDetail);
    Page<StockTakeItemListDto> newPage(Page page, Wrapper queryWrapper);
}
