package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.entity.ActionRecord;
import com.fixedasset.entity.ProductType;
import com.fixedasset.mapper.ActionRecordMapper;
import com.fixedasset.mapper.ProductTypeMapper;
import com.fixedasset.service.ProductTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements ProductTypeService {

    @Resource private ProductTypeMapper productTypeMapper;

    @Resource private ActionRecordMapper actionRecordMapper;

    @Resource private ActionRecord actionRecord;

    @Resource private  ProductType productType;

    public void createOne(ProductType productType) {
        productType.setStatu(1);
        productType.setCreated(OffsetDateTime.now());
        productTypeMapper.insert(productType);

        actionRecord.setActionName("Create");
        actionRecord.setActionMethod("POST");
        actionRecord.setActionFrom("Product Type");
        actionRecord.setActionData(productType.toString());
        actionRecord.setActionSuccess("Success");
        actionRecord.setCreated(OffsetDateTime.now());
        createdAction(actionRecord);
    }

    public void voidOne(Long id) {
        productType.setId(id);
        productType.setStatu(0);
        productTypeMapper.updateById(productType);

        actionRecord.setActionName("Remove");
        actionRecord.setActionMethod("DELETE");
        actionRecord.setActionFrom("Product Type");
        actionRecord.setActionData(productType.toString());
        actionRecord.setActionSuccess("Success");
        actionRecord.setCreated(OffsetDateTime.now());
        createdAction(actionRecord);
    }

    public void updateOne(ProductType productType) {
        productType.setUpdated(OffsetDateTime.now());
        productTypeMapper.updateById(productType);

        actionRecord.setActionName("Update");
        actionRecord.setActionMethod("POST");
        actionRecord.setActionFrom("Product Type");
        actionRecord.setActionData(productType.toString());
        actionRecord.setActionSuccess("Success");
        actionRecord.setCreated(OffsetDateTime.now());
        createdAction(actionRecord);
    }

    public List<ProductType> getAll() {
        return productTypeMapper.getAll();
    }

    public int createdAction(ActionRecord actionRecord) {
        return actionRecordMapper.insert(actionRecord);
    }

}
