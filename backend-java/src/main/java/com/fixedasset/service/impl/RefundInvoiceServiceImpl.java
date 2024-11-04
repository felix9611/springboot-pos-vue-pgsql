package com.fixedasset.service.impl;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.dto.RefundInvoiceItemList;
import com.fixedasset.entity.Invoice;
import com.fixedasset.entity.RefundInvoice;
import com.fixedasset.entity.RefundInvoiceItem;
import com.fixedasset.mapper.RefundInvoiceItemMapper;
import com.fixedasset.mapper.RefundInvoiceMapper;
import com.fixedasset.service.ActionRecordService;
import com.fixedasset.service.InvoiceService;
import com.fixedasset.service.RefundInvoiceService;

@Service
public class RefundInvoiceServiceImpl extends ServiceImpl<RefundInvoiceMapper, RefundInvoice> implements RefundInvoiceService{
    
    @Resource private RefundInvoiceMapper refundInvoiceMapper;

    @Resource private RefundInvoiceItemMapper refundInvoiceItemMapper;

    @Resource private RefundInvoice refundInvoice;

    @Resource private RefundInvoiceItem refundInvoiceItem;

    @Resource private ActionRecordService actionRecordService;

    @Resource private InvoiceService invoiceService;

    @Resource private Invoice invoice;


    public RefundInvoice getData(int invoiceId) {
        LambdaQueryWrapper<RefundInvoice> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(RefundInvoice::getInvoiceId, invoiceId);
        queryWrapper.eq(RefundInvoice::getStatus, 1);

        RefundInvoice getOne = refundInvoiceMapper.selectOne(queryWrapper);

        LambdaQueryWrapper<RefundInvoiceItem> queryWrapperItem = Wrappers.lambdaQuery();
        queryWrapperItem.eq(RefundInvoiceItem::getRefundInvoiceId, getOne.getId());
        queryWrapperItem.eq(RefundInvoiceItem::getStatus, 1);

        List<RefundInvoiceItemList> refundInvoiceItems = refundInvoiceItemMapper.listData(queryWrapperItem);
        getOne.setRefundInvoiceItems(refundInvoiceItems);

        return getOne;
    }


    public void create(RefundInvoice newData) {
        LambdaQueryWrapper<RefundInvoice> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(RefundInvoice::getInvoiceId, newData.getInvoiceId());
        queryWrapper.eq(RefundInvoice::getStatus, 1);
        
        RefundInvoice checkOne = refundInvoiceMapper.selectOne(queryWrapper);

        if (checkOne == null) { 
            LambdaQueryWrapper<Invoice> queryWrapperInvoice = Wrappers.lambdaQuery();
            queryWrapperInvoice.eq(Invoice::getId, newData.getInvoiceId());
            queryWrapperInvoice.eq(Invoice::getVoidNum, 1);

            Invoice checkInvoice = invoiceService.getOne(queryWrapperInvoice);

            if (checkInvoice == null) {
                invoice.setId(Long.valueOf(newData.getInvoiceId()));
                invoice.setVoidAt(OffsetDateTime.now());
                invoice.setVoidNum(2);
                invoiceService.updateById(invoice);

                newData.setCaseNo(getNewCode());
                newData.setCreatedAt(OffsetDateTime.now());
                newData.setStatus(1);

                refundInvoiceMapper.insert(newData);

                actionRecordService.createdAction(
                    "Create", 
                    "POST",
                    "Refund Inovice", 
                    newData.toString(), 
                    "Success"
                );


                if (newData.getNewRefundInvoiceItems().size() > 0) {
                    for (RefundInvoiceItem item : newData.getNewRefundInvoiceItems()) {
                        item.setRefundInvoiceId(Math.toIntExact(newData.getId()));
                        item.setStatus(1);
                        item.setCreatedAt(OffsetDateTime.now());

                        refundInvoiceItemMapper.insert(item);
                        actionRecordService.createdAction(
                            "Create", 
                            "POST",
                            "Refund Inovice  Item", 
                            item.toString(), 
                            "Success"
                        );

                    }
                }

            } else {              

                throw new RuntimeException("Cannot refund!");
            }

        } else {
            actionRecordService.createdAction(
                "Create", 
                "POST",
                "Inovice Refund", 
                newData.toString(), 
                "Failure"
            );

            throw new RuntimeException("Exist this data!");
        }
    }

    public void update(RefundInvoice record) {
        LambdaQueryWrapper<RefundInvoice> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(RefundInvoice::getInvoiceId, record.getInvoiceId());
        queryWrapper.eq(RefundInvoice::getStatus, 1);
        
        RefundInvoice checkOne = refundInvoiceMapper.selectOne(queryWrapper);

        if (checkOne.getId().equals(record.getId())) {
            LambdaQueryWrapper<Invoice> queryWrapperInvoice = Wrappers.lambdaQuery();
            queryWrapperInvoice.eq(Invoice::getId, record.getInvoiceId());
            queryWrapperInvoice.eq(Invoice::getVoidNum, 2);

            Invoice checkInvoice = invoiceService.getOne(queryWrapperInvoice);

            if (checkInvoice == null) { 
                record.setUpdatedAt(OffsetDateTime.now());

                refundInvoiceMapper.updateById(record);

                actionRecordService.createdAction(
                    "Update", 
                    "POST", 
                    "Refund Inovice", 
                    record.toString(), 
                    "Success"
                );

                if (record.getRefundInvoiceItems().size() > 0) {
                    for (RefundInvoiceItem item : record.getRefundInvoiceItems()) {

                        item.setUpdatedAt(OffsetDateTime.now());

                        refundInvoiceItemMapper.updateById(item);

                        actionRecordService.createdAction(
                            "Update", 
                            "POST", 
                            "Refund Inovice Item", 
                            item.toString(), 
                            "Success"
                        );
                    }
                }
            } else {
                throw new RuntimeException("Cannot refund again!");
            }
            
        } else {
            actionRecordService.createdAction(
                "Update", 
                "POST",
                "Inovice Refund", 
                record.toString(), 
                "Failure"
            );

            throw new RuntimeException("Exist this data!");
        }
    }

    public void cancelRefund(Long id) {
        LambdaQueryWrapper<RefundInvoice> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(RefundInvoice::getId, id);
        queryWrapper.eq(RefundInvoice::getStatus, 0);
        
        RefundInvoice checkOne = refundInvoiceMapper.selectOne(queryWrapper);

        if (checkOne.getId().equals(id)) {

            refundInvoice.setId(id);
            refundInvoice.setStatus(0);
            refundInvoice.setUpdatedAt(OffsetDateTime.now());

            refundInvoiceMapper.updateById(refundInvoice);

        } else {
            actionRecordService.createdAction(
                "Cancel Refund", 
                "DELETE",
                "Inovice Refund", 
                id.toString(), 
                "Failure"
            );

            throw new RuntimeException("Cannot refund again!");
        }
    }

    public String getNewCode() {
        LambdaQueryWrapper<RefundInvoice> lambdaQueryWrapper = Wrappers.lambdaQuery();

        List<Object> codes = refundInvoiceMapper.selectObjs(lambdaQueryWrapper);
        AtomicReference<Integer> maxCodes = new AtomicReference<>(0);

        codes.forEach(o -> {
            String code = String.valueOf(o);
            if (code.length() >= 6) {
                Integer one = Integer.parseInt(code.substring(code.length() - 5));
                if (one > maxCodes.get()) {
                    maxCodes.set(one);
                }
            }

        });
        return padRight(maxCodes.get() + 1, 6, "0");
    }

    public static String padRight(int oriStr, int len, String alexi) {
        StringBuilder str = new StringBuilder();
        int strlen = String.valueOf(oriStr).length();
        if (strlen < len) {
            for (int i = 0; i < len - strlen; i++) {
                str.append(alexi);
            }
        }
        str.append(oriStr);
        return str.toString();
    }
}
