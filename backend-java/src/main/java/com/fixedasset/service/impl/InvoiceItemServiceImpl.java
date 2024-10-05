package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.dto.InvoiceItemListDto;
import com.fixedasset.dto.charts.QueryTotalShop;
import com.fixedasset.entity.InvoiceItem;
import com.fixedasset.mapper.InvoiceItemMapper;
import com.fixedasset.service.InvoiceItemService;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.annotation.Resource;

@Service
public class InvoiceItemServiceImpl extends ServiceImpl<InvoiceItemMapper, InvoiceItem> implements InvoiceItemService {
    @Resource InvoiceItemMapper invoiceItemMapper;

    public void saveItem(InvoiceItem invoiceItem) {
        invoiceItemMapper.insert(invoiceItem);
    }

    public List<InvoiceItemListDto> listByInvoiceId(Long invoiceId) {
        return invoiceItemMapper.listByInvoiceId(invoiceId);
    }
}
