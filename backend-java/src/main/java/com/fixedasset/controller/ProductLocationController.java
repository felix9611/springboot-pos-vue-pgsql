package com.fixedasset.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.common.lang.Result;
import com.fixedasset.dto.ProductLocationChangeDto;
import com.fixedasset.dto.ProductLocationListDto;
import com.fixedasset.entity.ProductLocation;
import com.fixedasset.service.ProductLocationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.annotation.Resource;

@Tag(name = "Product Location")
@RestController
@RequestMapping("/product/location")
public class ProductLocationController extends BaseController{

    @Resource private ProductLocationService productLocationService;


    @Operation(summary = "Save to location")    
    @PostMapping("/save")
    public Result saveQty(@RequestBody List<ProductLocation> productLocations) {
        for (ProductLocation productLocation : productLocations) {
            ProductLocation oldRecord = productLocationService.findOne(productLocation);
            if (oldRecord != null) {
                productLocationService.updateQty(productLocation);
            } else if (oldRecord == null) {
                productLocationService.saveProductLoc(productLocation);
            }
        }
        return Result.succ(productLocations);
    }

    @Operation(summary = "Find by location & product")    
    @PostMapping("/find")
    public Result findPlace(@RequestBody ProductLocation productLocation) {
        return Result.succ(productLocationService.findOne(productLocation));
    }

    @Operation(summary = "Renew data")   
    @PostMapping("/renew")
    public Result renewPlace(@RequestBody List<ProductLocationChangeDto> productLocationChangeDtos) {
        for (ProductLocationChangeDto productLocationChangeDto : productLocationChangeDtos) {
            productLocationService.changePlace(productLocationChangeDto);
        }
        return Result.succ(productLocationChangeDtos);
    }

    @Operation(summary = "Stock out")   
    @PostMapping("/stock/out")
    public Result stockOut(@RequestBody List<ProductLocation> productLocationChangeDtos) {
        for (ProductLocation productLocationChangeDto : productLocationChangeDtos) {
            productLocationService.changeQty(productLocationChangeDto);
        }
        return Result.succ(productLocationChangeDtos);
    }

    @Operation(summary = "Page and list")   
    @PostMapping("/list")
    public Result list(@RequestBody ProductLocation productLocation) {
        Page page = new Page(productLocation.getPage(), productLocation.getLimit());
        LambdaQueryWrapper<ProductLocation> queryWrapper = Wrappers.lambdaQuery();

        if(!(productLocation.getLocationId() == 0)) {
            queryWrapper.eq(ProductLocation::getLocationId, productLocation.getLocationId());
        }

        Page<ProductLocationListDto> iPage = productLocationService.newPage(page, queryWrapper);
        return Result.succ(iPage);
    }

    @Operation(summary = "Lists all")   
    @PostMapping("/list/all")
    public Result listAll(@RequestBody ProductLocation productLocation) {
        LambdaQueryWrapper<ProductLocation> queryWrapper = Wrappers.lambdaQuery();

        if(!(productLocation.getLocationId() == 0)) {
            queryWrapper.eq(ProductLocation::getLocationId, productLocation.getLocationId());
        }

        return Result.succ(productLocationService.listAll(queryWrapper));
    }

}
