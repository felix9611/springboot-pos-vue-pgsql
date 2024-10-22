package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.entity.ActionRecord;
import com.fixedasset.entity.ProductType;
import com.fixedasset.mapper.ActionRecordMapper;
import com.fixedasset.mapper.ProductTypeMapper;
import com.fixedasset.service.ActionRecordService;
import com.fixedasset.service.ProductTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ProductTypeServiceImpl extends ServiceImpl<ProductTypeMapper, ProductType> implements ProductTypeService {

    @Resource private ProductTypeMapper productTypeMapper;

    @Resource private ActionRecordService actionRecordService;

    @Resource private  ProductType productType;

    public void createOne(ProductType productType) {
        LambdaQueryWrapper<ProductType> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductType::getTypeCode, productType.getTypeCode());
        queryWrapper.eq(ProductType::getStatu, 1);
        ProductType check = productTypeMapper.selectOne(queryWrapper);
        if (check == null) {
            productType.setStatu(1);
            productType.setCreated(OffsetDateTime.now());
            productTypeMapper.insert(productType);

            actionRecordService.createdAction(
                "Create", 
                "POST", 
                "Product Type", 
                productType.toString(), 
                "Success"
            );
        } else {
            actionRecordService.createdAction(
                "Create", 
                "POST", 
                "Product Type", 
                productType.toString(), 
                "Failure"
            );
            throw new RuntimeException("Exist in records!");
        }
    }

    public void batchImport(List<ProductType> productTypes) {
        for(ProductType productType : productTypes) {
            createOne(productType);
        }
    }

    public void voidOne(Long id) {
        LambdaQueryWrapper<ProductType> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductType::getId, id);
        queryWrapper.eq(ProductType::getStatu, 1);
        ProductType check = productTypeMapper.selectOne(queryWrapper);
        if (check != null) {
            productType.setId(id);
            productType.setStatu(0);
            productTypeMapper.updateById(productType);

            actionRecordService.createdAction(
                "Remove", 
                "DELETE", 
                "Product Type", 
                productType.toString(), 
                "Success"
            );
        } else {
            actionRecordService.createdAction(
                "Remove", 
                "DELETE", 
                "Product Type", 
                productType.toString(), 
                "Failure"
            );
            throw new RuntimeException("No active data in records!");
        }
    }

    public void updateOne(ProductType productType) {
        LambdaQueryWrapper<ProductType> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductType::getId, productType.getId());
        queryWrapper.eq(ProductType::getStatu, 1);
        ProductType check = productTypeMapper.selectOne(queryWrapper);
        if (check != null) {
            productType.setUpdated(OffsetDateTime.now());
            productTypeMapper.updateById(productType);

            actionRecordService.createdAction(
                "Update", 
                "POST", 
                "Product Type", 
                productType.toString(), 
                "Success"
            );
        } else {
            actionRecordService.createdAction(
                "Update", 
                "POST", 
                "Product Type", 
                productType.toString(), 
                "Failure"
            );
            throw new RuntimeException("No active data in records!");
        }
    }

    public List<ProductType> getAll() {
        LambdaQueryWrapper<ProductType> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductType::getStatu, 1);
        return productTypeMapper.selectList(queryWrapper);
    }

}
