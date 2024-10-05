package com.fixedasset.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.common.lang.Result;
import com.fixedasset.entity.ProductType;
import com.fixedasset.service.ProductTypeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/product/type")
public class ProductTypeController extends BaseController{
    @Resource private ProductTypeService productTypeService;

    @PostMapping("/create")
    public Result save(@RequestBody ProductType productType) {
        productTypeService.createOne(productType);
        return Result.succ(productType);
    }


    @PostMapping("/update")
    public Result update(@RequestBody ProductType productType) {
        productTypeService.updateOne(productType);
        return Result.succ(productType);
    }

    @GetMapping("/getAll")
    public Result getAll() {
        return Result.succ(productTypeService.getAll());
    }

    @DeleteMapping("/void/{id}")
    public Result voidOne(@PathVariable("id")Long id) {
        productTypeService.voidOne(id);
        return Result.succ("");
    }

    @GetMapping("/{id}")
    public Result getOne(@PathVariable("id")Long id) {
        return Result.succ(productTypeService.getById(id));
    }

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
