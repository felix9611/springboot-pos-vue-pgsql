package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.entity.Payment;
import com.fixedasset.mapper.PaymentMapper;
import com.fixedasset.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PaymentServiceImpl extends ServiceImpl<PaymentMapper, Payment> implements PaymentService {
    @Resource PaymentMapper paymentMapper;

    @Resource Payment payment;

    public List<Payment> listByInvId(int invoiceId) {
        LambdaQueryWrapper<Payment> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Payment::getInvoiceId, invoiceId);
        return paymentMapper.selectList(queryWrapper);
    }
}
