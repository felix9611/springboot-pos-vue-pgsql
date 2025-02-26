package com.fixedasset.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixedasset.entity.PromotionCatelogType;

public interface PromotionCatelogTypeService extends IService<PromotionCatelogType> {
    PromotionCatelogType getById(Long id);
    List<PromotionCatelogType> getAll();
    public void voidData(Long id);
    void create(PromotionCatelogType promotionCatelogType);
    void update(PromotionCatelogType promotionCatelogType);
}
