package com.fixedasset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixedasset.entity.Payment;

import java.util.List;

public interface PaymentService extends IService<Payment> {
    List<Payment> listByInvId(int invoiceId);
}
