package com.fixedasset.dto;

import com.fixedasset.entity.InvoiceItem;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

@EqualsAndHashCode(callSuper = true)
@Data
public class InvoiceItemListDto extends InvoiceItem {
    @Transient private String productCode;
    @Transient private String productName;
}
