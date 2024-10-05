package com.fixedasset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixedasset.dto.InvoiceItemListDto;
import com.fixedasset.entity.InvoiceItem;

import java.util.List;

public interface InvoiceItemService extends IService<InvoiceItem> {
    void saveItem(InvoiceItem invoiceItem);
    List<InvoiceItemListDto> listByInvoiceId(Long invoiceId);
}
