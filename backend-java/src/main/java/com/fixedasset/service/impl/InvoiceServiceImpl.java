package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.dto.InvoiceItemListDto;
import com.fixedasset.dto.InvoiceListDto;
import com.fixedasset.dto.charts.InvoiceSalesDataResult;
import com.fixedasset.dto.charts.QueryCountShop;
import com.fixedasset.dto.charts.QueryCountYearWeek;
import com.fixedasset.dto.charts.QueryTotalShop;
import com.fixedasset.dto.charts.QueryTotalYearWeek;
import com.fixedasset.entity.InvRecord;
import com.fixedasset.entity.Invoice;
import com.fixedasset.entity.InvoiceItem;
import com.fixedasset.entity.Payment;
import com.fixedasset.entity.ProductLocation;
import com.fixedasset.mapper.InvoiceMapper;
import com.fixedasset.service.InvRecordService;
import com.fixedasset.service.InvoiceItemService;
import com.fixedasset.service.InvoiceService;
import com.fixedasset.service.PaymentService;
import com.fixedasset.service.ProductLocationService;

import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceMapper, Invoice> implements InvoiceService {
    @Resource private InvoiceMapper invoiceMapper;
    @Resource private Invoice invoice;
    @Resource private InvoiceItemService invoiceItemService;
    @Resource private PaymentService paymentService;
    @Resource private ProductLocation productLocation;
    @Resource private ProductLocationService productLocationService;
    @Resource private InvRecordService invRecordService;
    @Resource private InvRecord invRecord;

    public void saveNew(Invoice invoice) {
        invoice.setNumber("INV" + this.getNewCode());
        invoice.setCreatedAt(OffsetDateTime.now());
        invoice.setVoidNum(0);
        invoice.setTaxRefNo(taxNumberRandom());
        invoiceMapper.insert(invoice);

        List<InvoiceItem> invoiceItems = invoice.getNewInvoiceItems();
        List<Payment> payments = invoice.getNewPaymentItems();

        for (Payment payment : payments) {
            payment.setInvoiceId(Math.toIntExact(invoice.getId()));
            payment.setPaymentTime(OffsetDateTime.now());
            paymentService.save(payment);
        }  

       // if (invoiceItems.size() > 0) {
            for (InvoiceItem invoiceItem : invoiceItems) {

                invoiceItem.setInvoiceId(Math.toIntExact(invoice.getId()));

                productLocation.setProductId(invoiceItem.getProductId());
                productLocation.setLocationId(invoice.getLocationId());
    
                ProductLocation oldRecord = productLocationService.findOne(productLocation);

                if (invoiceItem.getQty() > oldRecord.getQty()) {
                    throw new RuntimeException("No good data in the store!");
                } else {
                    int renewQty = oldRecord.getQty() - invoiceItem.getQty();
                    double newTotalPrice = oldRecord.getTotalPrice() - (invoiceItem.getQty() * invoiceItem.getPrice());

                    productLocation.setId(oldRecord.getId());
                    productLocation.setProductId(invoiceItem.getProductId());
                    productLocation.setLocationId(invoice.getLocationId());
                    productLocation.setQty(renewQty);
                    productLocation.setTotalPrice(newTotalPrice);

                    productLocationService.updateData(productLocation);


                    invRecord.setQty(-invoiceItem.getQty());
                    invRecord.setProductId(invoiceItem.getProductId());
                    invRecord.setLocFrom(oldRecord.getLocationId());
                    invRecord.setLocTo(-invoice.getLocationId());
                    invRecord.setCost(invoiceItem.getQty() * invoiceItem.getPrice());
                    invRecord.setTimeAt(OffsetDateTime.now());

                    invRecordService.saveRecord(invRecord);

                    invoiceItemService.saveItem(invoiceItem);
                }
            }
                
    //    if (payments.size() > 0) {
             
     //   }
        


    }

    public Invoice findId(String number) {
        LambdaQueryWrapper<Invoice> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Invoice::getNumber, number);
        Invoice invoice = invoiceMapper.selectOne(queryWrapper);

        List<InvoiceItemListDto> invoiceItems = invoiceItemService.listByInvoiceId(invoice.getId());
        invoice.setInvoiceItems(invoiceItems);

        return invoice;
    }

    public Page<InvoiceListDto> listAll(Page page, Wrapper queryWrapper) {
        return invoiceMapper.listAll(page, queryWrapper);
    }

    public void voidInv(Long id) {
        invoice.setId(id);
        invoice.setVoidNum(1);
        invoice.setVoidAt(OffsetDateTime.now());
        invoiceMapper.updateById(invoice);
    }

    public InvoiceListDto selectOneItem(Long id) {
        InvoiceListDto invoice = invoiceMapper.selectOneId(id);

        List<InvoiceItemListDto> invoiceItems = invoiceItemService.listByInvoiceId(id);
        invoice.setInvoiceItems(invoiceItems);

        List<Payment> payments = paymentService.listByInvId(Math.toIntExact(id));
        invoice.setPaymentItems(payments);

        return invoice;
    }

    public String taxNumberRandom() {
        Random random = new Random();
        Long tenDigitNumber = 1000000000L + (long)(random.nextDouble() * 9000000000L);
        String numberAsString = String.valueOf(tenDigitNumber);
        return numberAsString;
    }

    public String getNewCode() {
        LambdaQueryWrapper<Invoice> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.select(Invoice::getNumber);

        List<Object> productCodes = invoiceMapper.selectObjs(lambdaQueryWrapper);
        AtomicReference<Integer> maxCodes = new AtomicReference<>(0);

        productCodes.forEach(o -> {
            String code = String.valueOf(o);
            if (code.length() >= 6) {
                Integer one = Integer.parseInt(code.substring(code.length() - 5));
                if (one > maxCodes.get()) {
                    maxCodes.set(one);
                }
            }

        });
        return padRight(maxCodes.get() + 1, 9, "0");
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


    public List<QueryTotalShop> queryTotalShop() {
        return invoiceMapper.queryTotalShop();
    }
    public List<QueryCountShop> queryCountShop() {
        return invoiceMapper.queryCountShop();
    }
    public List<QueryCountYearWeek> queryCountYearWeek(Invoice invoiceData) {
        LambdaQueryWrapper<Invoice> queryWrapper = Wrappers.lambdaQuery();
        if (invoiceData.getDateFrom() != null && invoiceData.getDateTo() != null) {
            queryWrapper.between(Invoice::getCreatedAt, invoiceData.getDateFrom(), invoiceData.getDateTo());
        }
        return invoiceMapper.queryCountYearWeek(queryWrapper);
    }
    public List<QueryTotalYearWeek> queryTotalYearWeek(Invoice invoiceData) {
        LambdaQueryWrapper<Invoice> queryWrapper = Wrappers.lambdaQuery();

        if (invoiceData.getDateFrom() != null && invoiceData.getDateTo() != null) {
            queryWrapper.between(Invoice::getCreatedAt, invoiceData.getDateFrom(), invoiceData.getDateTo());
        }

        return invoiceMapper.queryTotalYearWeek(queryWrapper);
    }

    public List<InvoiceSalesDataResult> querySalesByProduct(Invoice invoiceData) {
        LambdaQueryWrapper<Invoice> queryWrapper = saleDataGlobalFilter(invoiceData);
        return invoiceMapper.listSalesByProduct(queryWrapper);
    }

    public List<InvoiceSalesDataResult> querySalesByProductCounts(Invoice invoiceData) {
        LambdaQueryWrapper<Invoice> queryWrapper = saleDataGlobalFilter(invoiceData);
        return invoiceMapper.countSalesByProduct(queryWrapper);
    }

    public List<InvoiceSalesDataResult> queryTotalSalesByType(Invoice invoiceData) {
        LambdaQueryWrapper<Invoice> queryWrapper = saleDataGlobalFilter(invoiceData);
        return invoiceMapper.totalSalesByType(queryWrapper);
    }

    public List<InvoiceSalesDataResult> queryCountSalesByType(Invoice invoiceData) {
        LambdaQueryWrapper<Invoice> queryWrapper = saleDataGlobalFilter(invoiceData);

        return invoiceMapper.countSalesByType(queryWrapper);
    }

    public List<InvoiceSalesDataResult> queryCountSalesByDept(Invoice invoiceData) {
        LambdaQueryWrapper<Invoice> queryWrapper = saleDataGlobalFilter(invoiceData);

        return invoiceMapper.countSalesByDept(queryWrapper);
    }

    public List<InvoiceSalesDataResult> queryTotalSalesByDept(Invoice invoiceData) {
        LambdaQueryWrapper<Invoice> queryWrapper = saleDataGlobalFilter(invoiceData);

        return invoiceMapper.totalSalesByDept(queryWrapper);
    }

    public LambdaQueryWrapper<Invoice> saleDataGlobalFilter(Invoice invoiceData) {
        LambdaQueryWrapper<Invoice> queryWrapper = Wrappers.lambdaQuery();

   //     if (StringUtils.isBlank(invoiceData.getDateFrom()) && StringUtils.isNotBlank(invoiceData.getDateTo())) {
     //       queryWrapper.between(Invoice::getCreatedAt, invoiceData.getDateFrom(), invoiceData.getDateTo());
    //    }

        queryWrapper.eq(Invoice::getVoidNum, 0);
        queryWrapper.isNotNull(Invoice::getNumber);
        queryWrapper.notIn(Invoice::getLocationId, 0);

        return queryWrapper;
    }
}