package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.dto.ProductLocationChangeDto;
import com.fixedasset.dto.ProductLocationListDto;
import com.fixedasset.entity.InvRecord;
import com.fixedasset.entity.ProductList;
import com.fixedasset.entity.ProductLocation;
import com.fixedasset.mapper.ProductLocationMapper;
import com.fixedasset.service.InvRecordService;
import com.fixedasset.service.ProductLocationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class ProductLocationServiceImpl extends ServiceImpl<ProductLocationMapper, ProductLocation> implements ProductLocationService {

    @Resource private ProductLocationMapper productLocationMapper;

    @Resource private InvRecordService invRecordService;

    @Resource private InvRecord invRecord;

    public void saveProductLoc(ProductLocation productLocation) {
        productLocationMapper.insert(productLocation);

        invRecord.setQty(productLocation.getQty());
        invRecord.setProductId(productLocation.getProductId());
        invRecord.setLocFrom(0);
        invRecord.setLocTo(productLocation.getLocationId());
        invRecord.setCost(productLocation.getCost());
        invRecord.setTimeAt(OffsetDateTime.now());
        invRecordService.saveRecord(invRecord);
    }

    public void changePlace(ProductLocationChangeDto productLocationChangeDto) {
        productLocationMapper.updatePlaceQty(productLocationChangeDto.getQty(), productLocationChangeDto.getProductId(), productLocationChangeDto.getOldPlace());

        invRecord.setQty(productLocationChangeDto.getOtherQty());
        invRecord.setProductId(productLocationChangeDto.getProductId());
        invRecord.setLocFrom(productLocationChangeDto.getOldPlace());
        invRecord.setLocTo(productLocationChangeDto.getNewPlace());
        invRecord.setCost(productLocationChangeDto.getCost());
        invRecord.setTimeAt(OffsetDateTime.now());
        invRecordService.saveRecord(invRecord);

    }

    public void changeQty(ProductLocation productLocation) {
        LambdaQueryWrapper<ProductLocation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductLocation::getLocationId, productLocation.getLocationId());
        queryWrapper.eq(ProductLocation::getProductId, productLocation.getProductId());

        productLocationMapper.updatePlaceQty(productLocation.getQty(), productLocation.getProductId(), productLocation.getLocationId());

        invRecord.setQty(productLocation.getOtherQty());
        invRecord.setProductId(productLocation.getProductId());
        invRecord.setLocFrom(-productLocation.getLocationId());
        invRecord.setLocTo(0);
        invRecord.setCost(productLocation.getCost());
        invRecord.setTimeAt(OffsetDateTime.now());
        invRecordService.saveRecord(invRecord);

    }

    public ProductLocation findOne(ProductLocation productLocation) {
        LambdaQueryWrapper<ProductLocation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductLocation::getProductId, productLocation.getProductId());
        queryWrapper.eq(ProductLocation::getLocationId, productLocation.getLocationId());
        return productLocationMapper.selectOne(queryWrapper);
    }

    public Page<ProductLocationListDto> newPage(Page page, LambdaQueryWrapper<ProductLocation> queryWrapper) {
        return productLocationMapper.page(page, queryWrapper);
    }

    public List<ProductLocationListDto> listAll(LambdaQueryWrapper<ProductLocation> wrappers) {
        return productLocationMapper.listAll(wrappers);
    }

}
