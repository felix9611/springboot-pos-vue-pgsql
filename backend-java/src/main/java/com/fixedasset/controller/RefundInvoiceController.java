package com.fixedasset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fixedasset.common.lang.Result;
import com.fixedasset.entity.RefundInvoice;
import com.fixedasset.service.RefundInvoiceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Invoice Refund Data")
@RestController
@RequestMapping("/invoice/refund")
public class RefundInvoiceController {

    @Resource private RefundInvoiceService refundInvoiceService;

    @Operation(summary = "Create")
    @PostMapping("/create")
    public Result save(@RequestBody RefundInvoice refundInvoice) {
        refundInvoiceService.create(refundInvoice);
        return Result.succ(refundInvoice);
    }

    @Operation(summary = "Update")
    @PostMapping("/update")
    public Result update(@RequestBody RefundInvoice refundInvoice) {
        refundInvoiceService.update(refundInvoice);
        return Result.succ(refundInvoice);
    }

    @Operation(summary = "Get one record")
    @GetMapping("/{invoiceId}")
    public Result getOne(@PathVariable("invoiceId") int invoiceId) {
        return Result.succ(refundInvoiceService.getData(invoiceId));
    }

    @Operation(summary = "Cancel Refund")
    @GetMapping("/cancel/{id}")
    public Result getOne(@PathVariable("id") Long id) {
        refundInvoiceService.cancelRefund(id);
        return Result.succ(id);
    }
    
}
