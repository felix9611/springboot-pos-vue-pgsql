package com.fixedasset.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixedasset.entity.Promotion;

public interface PromotionService extends IService<Promotion>{
    Promotion getById(Long id);
    void create(Promotion newData);
    void update(Promotion data);
    void voidData(Long id);
    void removePromotionType(Long id);
    void removePromotionDepartment(Long id);
    void removePromotionLocation(Long id);
    List<Promotion> todayPromotion();
}
