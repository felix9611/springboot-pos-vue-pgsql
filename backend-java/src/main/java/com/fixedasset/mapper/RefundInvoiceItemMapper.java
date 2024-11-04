package com.fixedasset.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixedasset.dto.RefundInvoiceItemList;
import com.fixedasset.entity.RefundInvoiceItem;

public interface RefundInvoiceItemMapper extends BaseMapper<RefundInvoiceItem> {
    
    List<RefundInvoiceItemList> listData(@Param("ew") Wrapper queryWrapper);
}
