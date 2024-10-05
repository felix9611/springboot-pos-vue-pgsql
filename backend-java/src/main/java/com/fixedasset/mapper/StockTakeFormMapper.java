package com.fixedasset.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.dto.StockTakeFormListDto;
import com.fixedasset.entity.StockTakeForm;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface StockTakeFormMapper extends BaseMapper<StockTakeForm> {
    String query = "SELECT f.*," +
            "    l.place_name as placeName, l.place_code as placeCode " +
            "FROM stocktake_form AS f " +
            "LEFT JOIN location AS l on f.location_id = l.id";
    String wrapperSql = "SELECT * from ( " + query + " ) AS q ${ew.customSqlSegment}";
    @Select(wrapperSql)
    Page<StockTakeFormListDto> page(Page page, @Param("ew") Wrapper queryWrapper);
}
