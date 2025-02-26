package com.fixedasset.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.dto.ProductLocationListDto;
import com.fixedasset.entity.ProductLocation;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ProductLocationMapper extends BaseMapper<ProductLocation> {
    String listQuery = "Select pl.qty as qty, prl.product_name as  productName, prl.product_code as  productCode, " +
            "loc.place_code as placeCode , loc.place_name as placeName " +
            "from product_location as pl " +
            "left join product_list as prl on pl.product_id = prl.id " +
            "left join location as loc on pl.location_id = loc.id";
    String wrapperSql = "SELECT * from ( " + listQuery + " ) AS q ${ew.customSqlSegment}";
    @Select(wrapperSql)
    Page<ProductLocationListDto> page(Page page, @Param("ew") Wrapper queryWrapper);

    @Select(wrapperSql)
    List<ProductLocationListDto> listAll(@Param("ew") Wrapper queryWrapper);

    @Update("Update product_location SET qty = #{qty}, total_price = #{totalPrice} where product_id = #{productId} and location_id = #{locationId}")
    void updatePlaceQty(@Param("qty") int qty, @Param("totalPrice") double totalPrice, @Param("productId") int productId,  @Param("locationId") int locationId);

    List<ProductLocationListDto> queryInStockQtys();
}

