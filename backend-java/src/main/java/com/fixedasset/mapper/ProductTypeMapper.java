package com.fixedasset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixedasset.entity.ProductType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductTypeMapper extends BaseMapper<ProductType> {

    @Select("Select * from product_type where statu = 1")
    List<ProductType> getAll();

}
