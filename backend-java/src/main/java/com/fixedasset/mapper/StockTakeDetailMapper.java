package com.fixedasset.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.dto.StockTakeItemListDto;
import com.fixedasset.entity.StockTakeDetail;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface StockTakeDetailMapper extends BaseMapper<StockTakeDetail> {
    String query = "SELECT d.*," +
            "p.product_code as productCode, p.product_name as productName, pl.qty as plQty, " +
            "    l.place_name as placeName, l.place_name as placeCode " +
            "FROM stocktake_detail AS d " +
            "LEFT JOIN product_list AS p ON d.product_id = p.id " +
            "LEFT JOIN product_location AS pl on (d.product_id = pl.product_id and d.location_id = pl.location_id) " +
            "LEFT JOIN location AS l on d.location_id = l.id";
    String wrapperSql = "SELECT * from ( " + query + " ) AS q ${ew.customSqlSegment}";
    @Select(wrapperSql)
    Page<StockTakeItemListDto> page(Page page, @Param("ew") Wrapper queryWrapper);
}
