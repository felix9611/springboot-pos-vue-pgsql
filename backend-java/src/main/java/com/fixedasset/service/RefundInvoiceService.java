package com.fixedasset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixedasset.entity.RefundInvoice;

public interface RefundInvoiceService extends IService<RefundInvoice>{
    void create(RefundInvoice newData);
    void update(RefundInvoice record);
    RefundInvoice getData(int invoiceId);
    void cancelRefund(Long id);
}
