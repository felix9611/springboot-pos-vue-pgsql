package com.fixedasset.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fixedasset.dto.InvoiceListDto;
import com.fixedasset.dto.charts.InvoiceSalesDataResult;
import com.fixedasset.dto.charts.QueryCountShop;
import com.fixedasset.dto.charts.QueryCountYearWeek;
import com.fixedasset.dto.charts.QueryTotalShop;
import com.fixedasset.dto.charts.QueryTotalYearWeek;
import com.fixedasset.entity.Invoice;

import java.util.List;

public interface InvoiceService extends IService<Invoice> {
    void saveNew(Invoice invoice);
    Invoice findId(String number);
    Page<InvoiceListDto> listAll(Page page, Wrapper queryWrapper);
    void voidInv(Long id);
    InvoiceListDto selectOneItem(Long id);
    List<QueryCountShop> queryCountShop();
    List<QueryTotalShop> queryTotalShop();
    List<QueryCountYearWeek> queryCountYearWeek(Invoice invoiceData);
    List<QueryTotalYearWeek> queryTotalYearWeek(Invoice invoiceData);
    List<InvoiceSalesDataResult> querySalesByProduct(Invoice invoiceData);
    List<InvoiceSalesDataResult> querySalesByProductCounts(Invoice invoiceData);
    List<InvoiceSalesDataResult> queryTotalSalesByType(Invoice invoiceData);
    List<InvoiceSalesDataResult> queryCountSalesByType(Invoice invoiceData);
    List<InvoiceSalesDataResult> queryTotalSalesByDept(Invoice invoiceData);
    List<InvoiceSalesDataResult> queryCountSalesByDept(Invoice invoiceData);
}
