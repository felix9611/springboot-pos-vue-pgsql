package com.fixedasset.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.common.lang.Result;
import com.fixedasset.dto.InvoiceListDto;
import com.fixedasset.entity.Invoice;
import com.fixedasset.entity.InvoiceItem;
import com.fixedasset.service.InvoiceItemService;
import com.fixedasset.service.InvoiceService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/invoice")
public class InvoiceController extends  BaseController{
    @Resource private InvoiceService invoiceService;
    @Resource private InvoiceItemService invoiceItemService;

    @PostMapping("/save")
    public Result saveName(@RequestBody Invoice invoice) {
        invoiceService.saveNew(invoice);
        return Result.succ(invoice);
    }

    @GetMapping("/{number}")
    public Result findOne(@PathVariable("number")String number) {
        return Result.succ(invoiceService.findId(number));
    }

    @GetMapping("/detail/{id}")
    public Result findOneDetail(@PathVariable("id")Long id) {
        return Result.succ(invoiceService.selectOneItem(id));
    }

    @PostMapping("/saveItem")
    public Result saveItems(@RequestBody InvoiceItem invoiceItem) {
        invoiceItemService.saveItem(invoiceItem);
        return Result.succ(invoiceItem);
    }

    @GetMapping("/item/{invoiceId}")
    public Result listItem(@PathVariable("invoiceId")Long invoiceId) {
        return Result.succ(invoiceItemService.listByInvoiceId(invoiceId));
    }

    @DeleteMapping("/void/{id}")
    public Result voidInvoice(@PathVariable("id")Long id) {
        invoiceService.voidInv(id);
        return Result.succ("");
    }

    @PostMapping("/list")
    public Result listInv(@RequestBody Invoice invoice) {
        Page<Invoice> page = new Page(invoice.getPage(), invoice.getLimit());
        LambdaQueryWrapper<Invoice> queryWrapper = Wrappers.lambdaQuery();
        
        if (!(invoice.getVoidNum()==0)) {
            queryWrapper.eq(Invoice::getVoidNum, invoice.getVoidNum());
        }

        if (invoice.getLocationId() > 0) {
            queryWrapper.eq(Invoice::getLocationId, invoice.getLocationId());
        }

        if (!(invoice.getNumber() == null)) {
            queryWrapper.like(Invoice::getNumber, invoice.getNumber());
        }

        Page<InvoiceListDto> iPage = invoiceService.listAll(page, queryWrapper);
        return  Result.succ(iPage);
    }

    // Charts
    @GetMapping("/queryCountShop")
    public Result queryCountShop() {
        return Result.succ(invoiceService.queryCountShop());
    }
    @GetMapping("/queryTotalShop")
    public Result queryTotalShop() {
        return Result.succ(invoiceService.queryTotalShop());
    }
    @PostMapping("/queryTotalYearWeek")
    public Result queryTotalYearWeek(@RequestBody Invoice invoice) {
        return Result.succ(invoiceService.queryTotalYearWeek(invoice));
    }
    @PostMapping("/queryCountYearWeek")
    public Result queryCountYearWeek(@RequestBody Invoice invoice) {
        return Result.succ(invoiceService.queryCountYearWeek(invoice));
    }

}
