package com.fixedasset.mapper;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.dto.InvoiceListDto;
import com.fixedasset.dto.charts.InvoiceSalesDataResult;
import com.fixedasset.dto.charts.QueryCountShop;
import com.fixedasset.dto.charts.QueryCountYearWeek;
import com.fixedasset.dto.charts.QueryTotalShop;
import com.fixedasset.dto.charts.QueryTotalYearWeek;
import com.fixedasset.entity.Invoice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InvoiceMapper extends BaseMapper<Invoice> {
    String listQuery = "SELECT inv.*, " +
            "m.name as memberName, m.phone as memberPhone, m.address as memberAddress, " +
            "mc.name as mcName , loc.place_code as placeCode, loc.place_name as placeName," +
            "loc.address as address, loc.zip_code as zipCode, loc.country as country" +
            " FROM invoice as inv " +
            "left join member as m on inv.member_id = m.id " +
            "left join member_class as mc on m.class  = mc.id " +
            "left join location as loc on inv.location_id = loc.id ";
    String wrapperSql = "SELECT * from ( " + listQuery + " ) AS q ${ew.customSqlSegment}";

    String selectOne = listQuery + " where inv.id = ${id}";

    List<QueryTotalYearWeek> queryTotalYearWeek(@Param("ew") Wrapper<Invoice> queryWrapper);

    List<QueryCountYearWeek> queryCountYearWeek(@Param("ew") Wrapper<Invoice> queryWrapper);

    List<QueryTotalShop> queryTotalShop();

    List<QueryCountShop> queryCountShop();

    @Select(wrapperSql)
    Page<InvoiceListDto> listAll(Page page, @Param("ew") Wrapper<Invoice> queryWrapper);

    @Select(selectOne)
    InvoiceListDto selectOneId(@Param("id")Long id);

    List<InvoiceSalesDataResult> listSalesByProduct(@Param("ew") Wrapper<Invoice> qWrapper);

    List<InvoiceSalesDataResult> countSalesByProduct(@Param("ew") Wrapper<Invoice> qWrapper);

    List<InvoiceSalesDataResult> totalSalesByType(@Param("ew") Wrapper<Invoice> qWrapper);

    List<InvoiceSalesDataResult> countSalesByType(@Param("ew") Wrapper<Invoice> qWrapper);

    List<InvoiceSalesDataResult> countSalesByDept(@Param("ew") Wrapper<Invoice> qWrapper);

    List<InvoiceSalesDataResult> totalSalesByDept(@Param("ew") Wrapper<Invoice> qWrapper);
}
