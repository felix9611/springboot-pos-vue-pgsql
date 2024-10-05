package com.fixedasset.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.dto.InvRecordListDto;
import com.fixedasset.entity.InvRecord;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface InvRecordMapper extends BaseMapper<InvRecord> {

    String listQuery = "SELECT ir.*, ir.time AS timeAt, " +
            "lf.place_code as placeFromCode, lf.place_name as placeFromName," +
            "lt.place_code as placeToCode, lt.place_name as placeToName," +
            "pli.product_code as productCode, pli.product_name as productName " +
            "FROM invrecord as ir " +
            "left join location as lf on ir.loc_from = lf.id " +
            "left join location as lt on ir.loc_to = lt.id " +
            "left join product_list as pli on ir.product_id = pli.id";
    String wrapperSql = "SELECT * from ( " + listQuery + " ) AS q ${ew.customSqlSegment}";
    @Select(wrapperSql)
    Page<InvRecordListDto> newPage(Page page,  @Param("ew") Wrapper queryWrapper);
}
