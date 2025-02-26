package com.fixedasset.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixedasset.entity.PromotionType;

public interface PromotionTypeMapper extends BaseMapper<PromotionType> {
    List<PromotionType> listPromotionTypeDetail(@Param("promotionId") int promotionId);
}
