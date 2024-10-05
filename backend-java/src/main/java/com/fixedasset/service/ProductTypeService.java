package com.fixedasset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixedasset.entity.ProductType;

import java.util.List;

public interface ProductTypeService extends IService<ProductType> {
    void createOne(ProductType productType);

    void voidOne(Long id);

    void updateOne(ProductType productType);

    List<ProductType> getAll();
}
