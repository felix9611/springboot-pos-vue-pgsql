package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.dto.StockTakeItemListDto;
import com.fixedasset.entity.StockTakeDetail;
import com.fixedasset.mapper.StockTakeDetailMapper;
import com.fixedasset.service.StockTakeDetailService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Service
public class StockTakeDetailServiceImpl extends ServiceImpl<StockTakeDetailMapper, StockTakeDetail> implements StockTakeDetailService {
    @Resource private StockTakeDetailMapper stockTakeDetailMapper;

    public void stockTakeItem(StockTakeDetail stockTakeDetail) {
        stockTakeDetail.setTimeAt(OffsetDateTime.now());
        stockTakeDetailMapper.insert(stockTakeDetail);
    }

    public Page<StockTakeItemListDto> newPage(Page page, Wrapper queryWrapper) {
        return stockTakeDetailMapper.page(page, queryWrapper);
    }
}
