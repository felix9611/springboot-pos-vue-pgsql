package com.fixedasset.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.common.lang.Result;
import com.fixedasset.dto.ProductListDto;
import com.fixedasset.entity.ProductList;
import com.fixedasset.entity.ProductListFile;
import com.fixedasset.service.ProductListFileService;
import com.fixedasset.service.ProductListService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/product")
public class ProductListController extends BaseController{

    @Resource private ProductListService productListService;

    @Resource private ProductListFileService productListFileService;

    @PostMapping("/create")
    public Result createOne(@RequestBody ProductList productList) {
        productListService.createOne(productList);
        return Result.succ(productList);
    }

    @PostMapping("/update")
    public Result updateOne(@RequestBody ProductList productList) {
        productListService.updateOne(productList);
        return Result.succ(productList);
    }

    @GetMapping("/{id}")
    public Result getOne(@PathVariable("id")Long id) {
        return Result.succ(productListService.findOneById(id));
    }

    @PostMapping("/findByCode")
    public Result findByCode(@RequestBody ProductList productList) {
        return Result.succ(productListService.findOne(productList));
    }

    @DeleteMapping("/void/{id}")
    public Result voidOne(@PathVariable("id")Long id) {
        productListService.voidOne(id);
        return Result.succ("");
    }

    @PostMapping("/list")
    public Result list(@RequestBody ProductList productList) {
        Page page = new Page(productList.getPage(), productList.getLimit());
        LambdaQueryWrapper<ProductList> queryWrapper = Wrappers.lambdaQuery();

        if (StringUtils.isNotBlank(productList.getProductCode())) {
            queryWrapper.like(ProductList::getProductCode, productList.getProductCode());
        }

        if (StringUtils.isNotBlank(productList.getProductName())) {
            queryWrapper.like(ProductList::getProductName, productList.getProductName());
        }

        if (!(productList.getTypeId() == 0)) {
            queryWrapper.eq(ProductList::getTypeId, productList.getTypeId());
        }

        if (!(productList.getDeptId() == 0)) {
            queryWrapper.eq(ProductList::getDeptId, productList.getDeptId());
        }

        queryWrapper.eq(ProductList::getStatu, 1);
        queryWrapper.orderByDesc(ProductList::getId);

        Page<ProductListDto> iPage = productListService.newPage(page, queryWrapper);
        return Result.succ(iPage);
    }

    @PostMapping("/list/all")
    public Result listAll(@RequestBody ProductList productList) {
        LambdaQueryWrapper<ProductList> queryWrapper = Wrappers.lambdaQuery();

        if (StringUtils.isNotBlank(productList.getProductCode())) {
            queryWrapper.like(ProductList::getProductCode, productList.getProductCode());
        }

        if (StringUtils.isNotBlank(productList.getProductName())) {
            queryWrapper.like(ProductList::getProductName, productList.getProductName());
        }

        if (!(productList.getTypeId() == 0)) {
            queryWrapper.eq(ProductList::getTypeId, productList.getTypeId());
        }

        if (!(productList.getDeptId() == 0)) {
            queryWrapper.eq(ProductList::getDeptId, productList.getDeptId());
        }

        queryWrapper.eq(ProductList::getStatu, 1);
        queryWrapper.orderByDesc(ProductList::getId);

        return Result.succ(productListService.listAll(queryWrapper));
    }

    @DeleteMapping("/file/void/{id}")
    public Result voidFileOne(@PathVariable("id")Long id) {
        productListFileService.removeFile(id);
        return Result.succ("");
    }

    @PostMapping("/file/upload")
    public Result uploadOne(@RequestBody ProductListFile productListFile) {
        productListFileService.saveListPicture(productListFile);
        return Result.succ(productListFile);
    }

    @PostMapping("/loadFile")
    public  Result loadFile(@RequestBody ProductListFile productListFile) {
        return Result.succ(productListFileService.getByAssetId(productListFile));
    }
}
