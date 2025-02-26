package com.fixedasset.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.common.lang.Result;
import com.fixedasset.entity.ProductType;
import com.fixedasset.entity.PromotionCatelogType;
import com.fixedasset.service.PromotionCatelogTypeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;


@Tag(name = "Promotion Catelog Type")
@RestController
@RequestMapping("/base/promotion-catelog")
public class PromotionCatelogTypeController extends BaseController {

    @Resource private PromotionCatelogTypeService promotionCatelogTypeService;

    @Operation(summary = "Create")
    @PostMapping("/create")
    public Result save(@RequestBody PromotionCatelogType promotionCatelogType) {
        promotionCatelogTypeService.create(promotionCatelogType);
        return Result.succ(promotionCatelogType);
    }

    @Operation(summary = "Update")
    @PostMapping("/update")
    public Result update(@RequestBody PromotionCatelogType promotionCatelogType) {
        promotionCatelogTypeService.update(promotionCatelogType);
        return Result.succ(promotionCatelogType);
    }

    @Operation(summary = "Get all")
    @GetMapping("/getAll")
    public Result getAll() {
        return Result.succ(promotionCatelogTypeService.getAll());
    }

    @Operation(summary = "Void by id")
    @DeleteMapping("/void/{id}")
    public Result voidOne(@PathVariable("id")Long id) {
        promotionCatelogTypeService.voidData(id);
        return Result.succ("");
    }

    @Operation(summary = "Get by id")
    @GetMapping("/{id}")
    public Result getOne(@PathVariable("id")Long id) {
        return Result.succ(promotionCatelogTypeService.getById(id));
    }

    @Operation(summary = "Page and list")
    @PostMapping("/list")
    public Result listAll(@RequestBody PromotionCatelogType data) {
        Page page = new Page(data.getPage(), data.getLimit());
        LambdaQueryWrapper<PromotionCatelogType> queryWrapper = Wrappers.lambdaQuery();

        if (StringUtils.isNotBlank(data.getName())) {
            queryWrapper.like(PromotionCatelogType::getCatelogCode, data.getName())
                        .or()
                        .like(PromotionCatelogType::getCatelogName, data.getName());
        }

        queryWrapper.eq(PromotionCatelogType::getStatu, 1);

        Page<PromotionCatelogType> iPage = promotionCatelogTypeService.page(page, queryWrapper);

        return Result.succ(iPage);
    }
    
}
