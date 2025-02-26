package com.fixedasset.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixedasset.entity.Promotion;

public interface PromotionMapper extends BaseMapper<Promotion> {

    @Select("SELECT * FROM promotion WHERE (period_start >= CURDATE() OR period_start < CURDATE() + INTERVAL 1 DAY) AND STATU = 1;")
    List<Promotion> listTodayPromotion();
}
