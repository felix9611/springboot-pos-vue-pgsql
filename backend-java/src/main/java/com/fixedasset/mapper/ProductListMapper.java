package com.fixedasset.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.dto.ProductListDto;
import com.fixedasset.dto.ProductLocationListDto;
import com.fixedasset.entity.ProductList;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductListMapper extends BaseMapper<ProductList> {
    String listQuery = "Select pl.*, " +
            "type.type_code as typeCode , type.type_name as typeName " +
            "from product_list as pl " +
            "left join product_type as type on pl.type_id = type.id";
    String wrapperSql = "SELECT * from ( " + listQuery + " ) AS q ${ew.customSqlSegment}";
    @Select(wrapperSql)
    Page<ProductListDto> page(Page page, @Param("ew") Wrapper queryWrapper);

    @Select(wrapperSql)
    List<ProductListDto> listAll(@Param("ew") Wrapper queryWrapper);
}
