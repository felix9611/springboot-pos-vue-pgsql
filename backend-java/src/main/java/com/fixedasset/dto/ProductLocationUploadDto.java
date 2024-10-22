package com.fixedasset.dto;

import javax.persistence.Transient;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode()
@Data
public class ProductLocationUploadDto {
    @Schema(description = "The Place Code")
    @Transient private String placeCode;

    @Schema(description = "The Place Name")
    @Transient private String placeName;
 
    @Schema(description = "Qtys")
    @Transient private int qty;

    @Schema(description = "Total Price")
    @Transient private double totalPrice;

    @Schema(description = "Stock Move to new location code")
    @Transient private String stockMovePlaceToCode;

    @Schema(description = "Stock Move to new location name")
    @Transient private String stockMovePlaceToName;

    @Schema(description = "Stock Move to new location qtys")
    @Transient private int stockMovePlaceToQty;

    @Schema(description = "Stock Move to new location total price")
    @Transient private double stockMovePlaceToTotalPrice;
}
