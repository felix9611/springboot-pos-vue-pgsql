package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.entity.ActionRecord;
import com.fixedasset.entity.ProductListFile;
import com.fixedasset.mapper.ActionRecordMapper;
import com.fixedasset.mapper.ProductListFileMapper;
import com.fixedasset.service.ProductListFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ProductListFileServiceImpl extends ServiceImpl<ProductListFileMapper, ProductListFile> implements ProductListFileService {

    @Resource private ProductListFileMapper productListFileMapper;

    @Resource private ActionRecordMapper actionRecordMapper;

    @Resource private ActionRecord actionRecord;

    @Resource private ProductListFile productListFile;

    public void saveListPicture(ProductListFile productListFile){

        productListFile.setStatu(1);
        productListFile.setCreated(OffsetDateTime.now());
        productListFileMapper.insert(productListFile);

        actionRecord.setActionName("Add");
        actionRecord.setActionMethod("POST");
        actionRecord.setActionFrom("Product List - Photo");
        actionRecord.setActionData("Any Data");
        actionRecord.setActionSuccess("Success");
        actionRecord.setCreated(OffsetDateTime.now());
        this.createdAction(actionRecord);
    }

    public List<ProductListFile> getByAssetId(ProductListFile productListFile) {
        LambdaQueryWrapper<ProductListFile> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductListFile::getStatu, 1);
        queryWrapper.eq(ProductListFile::getProductId, productListFile.getProductId());
        return productListFileMapper.selectList(queryWrapper);
    }

    public void removeFile(Long id) {
        productListFile.setId(id);
        productListFile.setStatu(0);
        productListFileMapper.updateById(productListFile);

        actionRecord.setActionName("REMOVE");
        actionRecord.setActionMethod("DELETE");
        actionRecord.setActionFrom("Product List - Photo");
        actionRecord.setActionData("Any Data");
        actionRecord.setActionSuccess("Success");
        actionRecord.setCreated(OffsetDateTime.now());
        this.createdAction(actionRecord);

    }

    public int createdAction(ActionRecord actionRecord) {
        return actionRecordMapper.insert(actionRecord);
    }

}
