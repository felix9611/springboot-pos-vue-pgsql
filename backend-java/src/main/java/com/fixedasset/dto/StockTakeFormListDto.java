package com.fixedasset.dto;

import com.fixedasset.entity.StockTakeForm;
import lombok.Data;

import javax.persistence.Transient;

@Data
public class StockTakeFormListDto extends StockTakeForm {
    @Transient private String placeCode;

    @Transient private String placeName;
}
