package com.fixedasset.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import javax.persistence.Transient;

@EqualsAndHashCode()
@Data
public class ProductLocationListDto {
    @Transient private String productCode;

    @Transient private String productName;

    @Transient private String placeCode;

    @Transient private String placeName;

    @Transient private int qty;

    @Transient private int inStockQtys;
}
