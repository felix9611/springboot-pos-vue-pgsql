package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.entity.ActionRecord;
import com.fixedasset.entity.Vendor;
import com.fixedasset.mapper.ActionRecordMapper;
import com.fixedasset.mapper.VendorMapper;
import com.fixedasset.service.VendorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class VendorServiceImpl extends ServiceImpl<VendorMapper, Vendor> implements VendorService {

    @Resource private VendorMapper vendorMapper;

    @Resource private ActionRecord actionRecord;

    @Resource private ActionRecordMapper actionRecordMapper;

    public void createOne(Vendor vendor){
        vendor.setStatu(1);
        vendor.setCreated(OffsetDateTime.now());
        vendorMapper.insert(vendor);

        actionRecord.setActionName("Save");
        actionRecord.setActionMethod("POST");
        actionRecord.setActionFrom("Vendor Manger");
        actionRecord.setActionData(vendor.toString());
        actionRecord.setActionSuccess("Success");
        actionRecord.setCreated(OffsetDateTime.now());
        this.createdAction(actionRecord);
    }

    public void updateOne(Vendor vendor){
        vendor.setUpdated(OffsetDateTime.now());
        vendorMapper.updateById(vendor);

        actionRecord.setActionName("Update");
        actionRecord.setActionMethod("POST");
        actionRecord.setActionFrom("Vendor Manger");
        actionRecord.setActionData(vendor.toString());
        actionRecord.setActionSuccess("Success");
        actionRecord.setCreated(OffsetDateTime.now());
        this.createdAction(actionRecord);
    }

    public void removeOne(Vendor vendor) {
        vendorMapper.updateById(vendor);

        actionRecord.setActionName("Remove");
        actionRecord.setActionMethod("DELETE");
        actionRecord.setActionFrom("Vendor Manger");
        actionRecord.setActionData(vendor.toString());
        actionRecord.setActionSuccess("Success");
        actionRecord.setCreated(OffsetDateTime.now());
        this.createdAction(actionRecord);
    }

    public Vendor findOne(Vendor vendor) {
        LambdaQueryWrapper<Vendor> queryWrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(vendor.getVendorCode())) {
            queryWrapper.eq(Vendor::getVendorCode, vendor.getVendorCode());
        }
        if (StringUtils.isNotBlank(vendor.getVendorName())) {
            queryWrapper.eq(Vendor::getVendorName, vendor.getVendorName());
        }
        queryWrapper.eq(Vendor::getStatu, 1);
        return vendorMapper.selectOne(queryWrapper);
    }

    public List<Vendor> getAll() { return vendorMapper.getALL(); }

    public int createdAction(ActionRecord actionRecord) {
        return actionRecordMapper.insert(actionRecord);
    }

}
