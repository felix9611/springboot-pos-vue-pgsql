package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.dto.InvoiceListDto;
import com.fixedasset.dto.charts.QueryCountShop;
import com.fixedasset.dto.charts.QueryCountYearWeek;
import com.fixedasset.dto.charts.QueryTotalShop;
import com.fixedasset.dto.charts.QueryTotalYearWeek;
import com.fixedasset.entity.Invoice;
import com.fixedasset.entity.ProductList;
import com.fixedasset.mapper.InvoiceMapper;
import com.fixedasset.service.InvoiceService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceMapper, Invoice> implements InvoiceService {
    @Resource private InvoiceMapper invoiceMapper;
    @Resource private Invoice invoice;

    public void saveNew(Invoice invoice) {
        invoice.setNumber("INV" + this.getNewCode());
        invoice.setCreatedAt(OffsetDateTime.now());
        invoice.setVoidNum(0);
        invoice.setTaxRefNo(taxNumberRandom());
        invoiceMapper.insert(invoice);
    }

    public Invoice findId(String number) {
        LambdaQueryWrapper<Invoice> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Invoice::getNumber, number);
        return invoiceMapper.selectOne(queryWrapper);
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
        return invoiceMapper.selectOneId(id);
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



}
