package com.fixedasset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixedasset.dto.InvoiceItemListDto;
import com.fixedasset.entity.InvoiceItem;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface InvoiceItemMapper extends BaseMapper<InvoiceItem> {
    @Select("SELECT ini.*, pl.product_code as productCode, pl.product_name as productName " +
            "FROM invoice_item as ini " +
            "left join product_list as pl on ini.product_id = pl.id where invoice_id = #{invoiceId}")
    List<InvoiceItemListDto> listByInvoiceId(@Param("invoiceId") Long invoiceId);
}
