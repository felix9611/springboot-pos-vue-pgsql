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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Tag(name = "Invoice")
@RestController
@RequestMapping("/invoice")
public class InvoiceController extends  BaseController{
    @Resource private InvoiceService invoiceService;
    @Resource private InvoiceItemService invoiceItemService;

    @Operation(summary = "Save Invoice")
    @PostMapping("/save")
    public Result saveName(@RequestBody Invoice invoice) {
        invoiceService.saveNew(invoice);
        return Result.succ(invoice);
    }

    @Operation(summary = "Get by invoice no.")
    @GetMapping("/{number}")
    public Result findOne(@PathVariable("number")String number) {
        return Result.succ(invoiceService.findId(number));
    }

    @Operation(summary = "Get detail by id")
    @GetMapping("/detail/{id}")
    public Result findOneDetail(@PathVariable("id")Long id) {
        return Result.succ(invoiceService.selectOneItem(id));
    }

    @Operation(summary = "Save items")
    @PostMapping("/saveItem")
    public Result saveItems(@RequestBody InvoiceItem invoiceItem) {
        invoiceItemService.saveItem(invoiceItem);
        return Result.succ(invoiceItem);
    }

    @Operation(summary = "Get items by invoice id")
    @GetMapping("/item/{invoiceId}")
    public Result listItem(@PathVariable("invoiceId")Long invoiceId) {
        return Result.succ(invoiceItemService.listByInvoiceId(invoiceId));
    }

    @Operation(summary = "Void by id")
    @DeleteMapping("/void/{id}")
    public Result voidInvoice(@PathVariable("id")Long id) {
        invoiceService.voidInv(id);
        return Result.succ("");
    }

    @Operation(summary = "Page and list")
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
    @Operation(summary = "Count by per shhop / location")
    @GetMapping("/queryCountShop")
    public Result queryCountShop() {
        return Result.succ(invoiceService.queryCountShop());
    }

    @Operation(summary = "Total sales income by per shhop / location")
    @GetMapping("/queryTotalShop")
    public Result queryTotalShop() {
        return Result.succ(invoiceService.queryTotalShop());
    }

    @Operation(summary = "Total sales income by Year & Week")
    @PostMapping("/queryTotalYearWeek")
    public Result queryTotalYearWeek(@RequestBody Invoice invoice) {
        return Result.succ(invoiceService.queryTotalYearWeek(invoice));
    }

    @Operation(summary = "Count by Year & Week")
    @PostMapping("/queryCountYearWeek")
    public Result queryCountYearWeek(@RequestBody Invoice invoice) {
        return Result.succ(invoiceService.queryCountYearWeek(invoice));
    }

    @Operation(summary = "Sales count by Product")
    @PostMapping("/querySalesByProduct")
    public Result querySalesByProduct(@RequestBody Invoice invoice) {
        return Result.succ(invoiceService.querySalesByProduct(invoice));
    }

    @Operation(summary = "Top 10 sales count by Product")
    @PostMapping("/querySalesByProductCounts")
    public Result querySalesByProductCounts(@RequestBody Invoice invoice) {
        return Result.succ(invoiceService.querySalesByProductCounts(invoice));
    }

    @Operation(summary = "Top 10 sales total income by Type")
    @PostMapping("/queryTotalSalesByType")
    public Result queryTotalSalesByType(@RequestBody Invoice invoice) {
        return Result.succ(invoiceService.queryTotalSalesByType(invoice));
    }

    @Operation(summary = "Top 10 sales count by Product")
    @PostMapping("/queryCountSalesByType")
    public Result queryCountSalesByType(@RequestBody Invoice invoice) {
        return Result.succ(invoiceService.queryCountSalesByType(invoice));
    }

    @Operation(summary = "Top 10 sales count by Department")
    @PostMapping("/queryCountSalesByDept")
    public Result queryCountSalesByDept(@RequestBody Invoice invoice) {
        return Result.succ(invoiceService.queryCountSalesByDept(invoice));
    }

    @Operation(summary = "Top 10 sales total income by Department")
    @PostMapping("/queryTotalSalesByDept")
    public Result queryTotalSalesByDept(@RequestBody Invoice invoice) {
        return Result.succ(invoiceService.queryTotalSalesByDept(invoice));
    }
    
}