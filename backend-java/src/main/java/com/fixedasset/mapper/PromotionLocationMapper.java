package com.fixedasset.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixedasset.entity.PromotionLocation;

public interface PromotionLocationMapper extends BaseMapper<PromotionLocation> {
    List<PromotionLocation> listPromotionLocationDetail(@Param("promotionId") int promotionId);
}
