package com.fixedasset.dto;

import javax.persistence.Transient;

import com.fixedasset.entity.RefundInvoiceItem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RefundInvoiceItemList extends RefundInvoiceItem {
    @Schema(description = "The Product Code")
    @Transient private String productCode;
    
    @Schema(description = "The Product Name")
    @Transient private String productName;
}
