package com.fixedasset.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fixedasset.dto.ProductListDto;
import com.fixedasset.entity.ProductList;

import java.util.List;

public interface ProductListService extends IService<ProductList> {
    void createOne(ProductList productList);
    void voidOne(Long id);
    void updateOne(ProductList productList);
    ProductList findOne(ProductList productList);
    Page<ProductListDto> newPage(Page page, LambdaQueryWrapper<ProductList> queryWrapper);
    List<ProductListDto> listAll(LambdaQueryWrapper<ProductList> queryWrapper);
}
