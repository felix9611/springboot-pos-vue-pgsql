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

    public void updateData(ProductLocation productLocation) {
        productLocationMapper.updateById(productLocation);
    }

    public void saveProductLoc(ProductLocation productLocation) {
        productLocationMapper.insert(productLocation);

        invRecord.setQty(productLocation.getQty());
        invRecord.setProductId(productLocation.getProductId());
        invRecord.setLocFrom(0);
        invRecord.setLocTo(productLocation.getLocationId());
        invRecord.setCost(productLocation.getTotalPrice());
        invRecord.setTimeAt(OffsetDateTime.now());
        invRecordService.saveRecord(invRecord);
    }

    public void changePlace(ProductLocationChangeDto productLocationChangeDto) {

        LambdaQueryWrapper<ProductLocation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductLocation::getLocationId, productLocationChangeDto.getOldPlace());
        queryWrapper.eq(ProductLocation::getProductId, productLocationChangeDto.getProductId());
        
        ProductLocation oldData = productLocationMapper.selectOne(queryWrapper);


        if (oldData == null) {
            throw new RuntimeException("No Exist in records!");
        } else {
            ProductLocation updateOldDataModel = new ProductLocation(); 
            updateOldDataModel.setId(oldData.getId()); 
            updateOldDataModel.setLocationId(oldData.getLocationId());
            updateOldDataModel.setProductId(oldData.getProductId());
            updateOldDataModel.setQty(oldData.getQty() - productLocationChangeDto.getQty());
            updateOldDataModel.setTotalPrice(oldData.getTotalPrice() - productLocationChangeDto.getCost());

            productLocationMapper.updateById(updateOldDataModel);

            LambdaQueryWrapper<ProductLocation> queryWrapperPlaceOldData = Wrappers.lambdaQuery();
            queryWrapperPlaceOldData.eq(ProductLocation::getLocationId, productLocationChangeDto.getNewPlace());
            queryWrapperPlaceOldData.eq(ProductLocation::getProductId, productLocationChangeDto.getProductId());
        
            ProductLocation newPlaceOldData = productLocationMapper.selectOne(queryWrapperPlaceOldData);
            if (newPlaceOldData != null) {

                Double oldTotalPrice = newPlaceOldData.getTotalPrice();

                int newQty = newPlaceOldData.getQty() + productLocationChangeDto.getQty();

                productLocationMapper.updatePlaceQty(newQty, oldTotalPrice,  productLocationChangeDto.getProductId(), productLocationChangeDto.getNewPlace());

                invRecord.setQty(productLocationChangeDto.getQty());
                invRecord.setProductId(productLocationChangeDto.getProductId());
                invRecord.setLocFrom(productLocationChangeDto.getOldPlace());
                invRecord.setLocTo(productLocationChangeDto.getNewPlace());
                invRecord.setCost(0);
                invRecord.setTimeAt(OffsetDateTime.now());
                invRecordService.saveRecord(invRecord);

                
            } else {
                ProductLocation newProductLocation = new ProductLocation();
                newProductLocation.setLocationId(productLocationChangeDto.getNewPlace());
                newProductLocation.setProductId(productLocationChangeDto.getProductId());
                newProductLocation.setQty(productLocationChangeDto.getQty());
                newProductLocation.setTotalPrice(0); 
                productLocationMapper.insert(newProductLocation);

                invRecord.setQty(productLocationChangeDto.getQty());
                invRecord.setProductId(productLocationChangeDto.getProductId());
                invRecord.setLocFrom(productLocationChangeDto.getOldPlace());
                invRecord.setLocTo(productLocationChangeDto.getNewPlace());
                invRecord.setCost(0);
                invRecord.setTimeAt(OffsetDateTime.now());
                invRecordService.saveRecord(invRecord);
            }
        }

   }

    public void updateQty(ProductLocation productLocation) {
        LambdaQueryWrapper<ProductLocation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductLocation::getLocationId, productLocation.getLocationId());
        queryWrapper.eq(ProductLocation::getProductId, productLocation.getProductId());
        
        ProductLocation oldRecord = productLocationMapper.selectOne(queryWrapper);

        if (oldRecord == null) {
            throw new RuntimeException("No Exist in records!");
        } else {
            Double oldTotalPrice = oldRecord.getTotalPrice();

            if (oldRecord.getTotalPrice() > 0 && productLocation.getTotalPrice() > 0) {
                oldTotalPrice = oldRecord.getTotalPrice() + productLocation.getTotalPrice();
            }

            int newQty = oldRecord.getQty() + productLocation.getQty();

            productLocationMapper.updatePlaceQty(newQty, oldTotalPrice,  productLocation.getProductId(), productLocation.getLocationId());

            invRecord.setQty(productLocation.getQty());
            invRecord.setProductId(productLocation.getProductId());
            invRecord.setLocFrom(productLocation.getLocationId());
            invRecord.setLocTo(0);
            invRecord.setCost(productLocation.getTotalPrice());
            invRecord.setTimeAt(OffsetDateTime.now());
            invRecordService.saveRecord(invRecord);
        }
    }

    public void changeQty(ProductLocation productLocation) {
        LambdaQueryWrapper<ProductLocation> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductLocation::getLocationId, productLocation.getLocationId());
        queryWrapper.eq(ProductLocation::getProductId, productLocation.getProductId());
        
        ProductLocation oldRecord = productLocationMapper.selectOne(queryWrapper);

        if (oldRecord == null) {
            throw new RuntimeException("No Exist in records!");
        } else {
            Double oldTotalPrice = oldRecord.getTotalPrice();

            if (oldRecord.getTotalPrice() > 0 && productLocation.getTotalPrice() > 0) {
                oldTotalPrice = oldRecord.getTotalPrice() - productLocation.getTotalPrice();
            }

            int newQty = oldRecord.getQty() - productLocation.getQty();

            productLocationMapper.updatePlaceQty(newQty, oldTotalPrice,  productLocation.getProductId(), productLocation.getLocationId());

            invRecord.setQty(productLocation.getQty());
            invRecord.setProductId(productLocation.getProductId());
            invRecord.setLocFrom(-productLocation.getLocationId());
            invRecord.setLocTo(0);
            invRecord.setCost(productLocation.getTotalPrice());
            invRecord.setTimeAt(OffsetDateTime.now());
            invRecordService.saveRecord(invRecord);
        }
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
