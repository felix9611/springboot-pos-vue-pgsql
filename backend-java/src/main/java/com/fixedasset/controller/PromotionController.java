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
import com.fixedasset.entity.Promotion;
import com.fixedasset.service.PromotionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Promotion")
@RestController
@RequestMapping("/base/promotion")
public class PromotionController extends BaseController {

    @Resource private PromotionService promotionService;

    @Operation(summary = "Void by id")
    @DeleteMapping("/void/{id}")
    public Result voidOne(@PathVariable("id") Long id) {
        promotionService.voidData(id);
        return Result.succ("");
    }

    @Operation(summary = "Promotion Location Void by id")
    @DeleteMapping("/location/void/{id}")
    public Result voidOnePromotionLocation(@PathVariable("id")Long id) {
        promotionService.removePromotionLocation(id);
        return Result.succ("");
    }

    @Operation(summary = "Promotion Type Void by id")
    @DeleteMapping("/type/void/{id}")
    public Result voidOnePromotionType(@PathVariable("id")Long id) {
        promotionService.removePromotionType(id);
        return Result.succ("");
    }

    @Operation(summary = "Promotion Department Void by id")
    @DeleteMapping("/department/void/{id}")
    public Result voidOnePromotionDepartment(@PathVariable("id")Long id) {
        promotionService.removePromotionDepartment(id);
        return Result.succ("");
    }

    @Operation(summary = "Get by id")
    @GetMapping("/{id}")
    public Result getOne(@PathVariable("id")Long id) {
        return Result.succ(promotionService.getById(id));
    }
    

    @Operation(summary = "Page and list")
    @PostMapping("/list")
    public Result listAll(@RequestBody Promotion promotion) {
        Page page = new Page(promotion.getPage(), promotion.getLimit());
        LambdaQueryWrapper<Promotion> queryWrapper = Wrappers.lambdaQuery();

        if (StringUtils.isNotBlank(promotion.getName())) {
            queryWrapper.like(Promotion::getPromotionCode, promotion.getName())
                        .or()
                        .like(Promotion::getPromotionName, promotion.getName());
        }

        queryWrapper.eq(Promotion::getStatu, 1);
        queryWrapper.orderByDesc(Promotion::getId);

        Page<Promotion> iPage = promotionService.page(page, queryWrapper);
        return Result.succ(iPage);
    }

    @Operation(summary = "Create")
    @PostMapping("/create")
    public Result save(@RequestBody Promotion promotion) {
        promotionService.create(promotion);
        return Result.succ(promotion);
    }

    @Operation(summary = "Update")
    @PostMapping("/update")
    public Result update(@RequestBody Promotion promotion) {
        promotionService.update(promotion);
        return Result.succ(promotion);
    }

    @Operation(summary = "List Today Promotion")
    @GetMapping("/today-promotion")
    public Result todayPromotion() {
        return Result.succ(promotionService.todayPromotion());
    }
}
