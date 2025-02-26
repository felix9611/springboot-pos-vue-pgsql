package com.fixedasset.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixedasset.entity.PromotionDepartment;

public interface PromotionDepartmentMapper extends BaseMapper<PromotionDepartment> {
    List<PromotionDepartment> listPromotionDepartmentDetail(@Param("promotionId") int promotionId);
}
