package com.fixedasset.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.common.lang.Result;
import com.fixedasset.dto.StockTakeFormListDto;
import com.fixedasset.dto.StockTakeItemListDto;
import com.fixedasset.entity.StockTakeDetail;
import com.fixedasset.entity.StockTakeForm;
import com.fixedasset.service.StockTakeDetailService;
import com.fixedasset.service.StockTakeFormService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Tag(name = "Stock Take")
@RestController
@RequestMapping("/stockTake")
public class StockTakeFormController extends BaseController {
    @Resource private StockTakeFormService stockTakeFormService;
    @Resource private StockTakeDetailService stockTakeDetailService;


    @Operation(summary = "Create")
    @PostMapping("/create")
    public Result saveRecord(@RequestBody StockTakeForm stockTakeForm) {
        stockTakeFormService.createStockTake(stockTakeForm);
        return Result.succ(stockTakeForm);
    }

    @Operation(summary = "Void by id")
    @DeleteMapping("/void/{id}")
    public Result voidForm(@PathVariable("id") Long id) {
        stockTakeFormService.voidForm(id);
        return Result.succ("");
    }

    @Operation(summary = "Finished by id")    
    @DeleteMapping("/finish/{id}")
    public Result finishForm(@PathVariable("id") Long id) {
        stockTakeFormService.finishForm(id);
        return Result.succ("");
    }

    @Operation(summary = "Get by id")
    @GetMapping("/{id}")
    public Result getForm(@PathVariable("id") Long id) {
        return Result.succ(stockTakeFormService.getFrom(id));
    }

    @Operation(summary = "Page and list")
    @PostMapping("/list")
    public Result listAll(@RequestBody StockTakeForm stockTakeForm) {
        Page page = new Page(stockTakeForm.getPage(), stockTakeForm.getLimit());
        LambdaQueryWrapper<StockTakeForm> queryWrapper = Wrappers.lambdaQuery();

        if (!(stockTakeForm.getStatus()==0)) {
            queryWrapper.eq(StockTakeForm::getStatus, stockTakeForm.getStatus());
        }
        queryWrapper.orderByDesc(StockTakeForm::getId);

        Page<StockTakeFormListDto> iPage = stockTakeFormService.newPage(page, queryWrapper);

        return Result.succ(iPage);
    }

    @Operation(summary = "Item stock take")
    @PostMapping("/item")
    public Result stockTakeItem(@RequestBody StockTakeDetail stockTakeDetail) {
        stockTakeDetailService.stockTakeItem(stockTakeDetail);
        return Result.succ(stockTakeDetail);
    }

    @Operation(summary = "Stock take detail list")
    @PostMapping("/detail/list")
    public Result stockTakeItemList(@RequestBody StockTakeDetail stockTakeDetail) {
        Page page = new Page(stockTakeDetail.getPage(), stockTakeDetail.getLimit());
        LambdaQueryWrapper<StockTakeDetail> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(StockTakeDetail::getFormId, stockTakeDetail.getFormId());
        queryWrapper.orderByDesc(StockTakeDetail::getTimeAt);

        Page<StockTakeItemListDto> iPage = stockTakeDetailService.newPage(page, queryWrapper);

        return Result.succ(iPage);
    }
}
