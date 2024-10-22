package com.fixedasset.dto;

import com.fixedasset.entity.InvoiceItem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

@EqualsAndHashCode(callSuper = true)
@Data
public class InvoiceItemListDto extends InvoiceItem {
    @Schema(description = "The product code, response only")
    @Transient private String productCode;

    @Schema(description = "The product name, response only")
    @Transient private String productName;
}

