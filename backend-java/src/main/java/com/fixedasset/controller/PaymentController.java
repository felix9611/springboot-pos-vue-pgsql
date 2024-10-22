package com.fixedasset.controller;

import com.fixedasset.common.lang.Result;
import com.fixedasset.entity.Payment;
import com.fixedasset.service.PaymentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.OffsetDateTime;

@Tag(name = "Payment")
@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Resource PaymentService paymentService;

    @Operation(summary = "Save data") 
    @PostMapping("/save")
    public Result savePay(@RequestBody Payment payment) {
        payment.setPaymentTime(OffsetDateTime.now());
        paymentService.save(payment);
        return Result.succ(payment);
    }

    @Operation(summary = "Get lists by invoice id") 
    @GetMapping("/list/{invoiceId}")
    public Result listItem(@PathVariable("invoiceId")int invoiceId) {
        return Result.succ(paymentService.listByInvId(invoiceId));
    }
}
