package com.fixedasset.dto;

import com.fixedasset.entity.StockTakeDetail;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

@EqualsAndHashCode()
@Data
public class StockTakeItemListDto extends StockTakeDetail {
    @Transient private String productCode;

    @Transient private String productName;
    @Transient private String placeCode;

    @Transient private String placeName;

    @Transient private int plQty;


}
