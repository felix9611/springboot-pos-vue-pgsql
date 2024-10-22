package com.fixedasset.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.common.lang.Result;
import com.fixedasset.entity.ProductType;
import com.fixedasset.service.ProductTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.annotation.Resource;

@Tag(name = "Product Type")
@RestController
@RequestMapping("/product/type")
public class ProductTypeController extends BaseController{
    @Resource private ProductTypeService productTypeService;

    @Operation(summary = "Create")
    @PostMapping("/create")
    public Result save(@RequestBody ProductType productType) {
        productTypeService.createOne(productType);
        return Result.succ(productType);
    }

    @Operation(summary = "Batch to import")
    @PostMapping("/batch-create")
    public Result batchCreate(@RequestBody List<ProductType> assetTypes) {
        productTypeService.batchImport(assetTypes);
        return Result.succ(assetTypes);
    }

    @Operation(summary = "Update")
    @PostMapping("/update")
    public Result update(@RequestBody ProductType productType) {
        productTypeService.updateOne(productType);
        return Result.succ(productType);
    }

    @Operation(summary = "Get all")
    @GetMapping("/getAll")
    public Result getAll() {
        return Result.succ(productTypeService.getAll());
    }

    @Operation(summary = "Void by id")
    @DeleteMapping("/void/{id}")
    public Result voidOne(@PathVariable("id")Long id) {
        productTypeService.voidOne(id);
        return Result.succ("");
    }

    @Operation(summary = "Get by id")
    @GetMapping("/{id}")
    public Result getOne(@PathVariable("id")Long id) {
        return Result.succ(productTypeService.getById(id));
    }

    @Operation(summary = "Page and list")
    @PostMapping("/list")
    public Result listAll(@RequestBody ProductType productType) {
        Page page = new Page(productType.getPage(), productType.getLimit());
        LambdaQueryWrapper<ProductType> queryWrapper = Wrappers.lambdaQuery();

        if (StringUtils.isNotBlank(productType.getTypeName())) {
            queryWrapper.like(ProductType::getTypeName, productType.getTypeName());
        }

        queryWrapper.eq(ProductType::getStatu, 1);
        queryWrapper.orderByDesc(ProductType::getId);

        Page<ProductType> iPage = productTypeService.page(page, queryWrapper);
        return Result.succ(iPage);
    }
}