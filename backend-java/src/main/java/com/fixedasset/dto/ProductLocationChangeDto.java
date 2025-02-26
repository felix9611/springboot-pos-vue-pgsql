package com.fixedasset.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProductLocationChangeDto {
    @Schema(description = "The product data id")
    private int productId;

    @Schema(description = "New location id")
    private int newPlace;

    @Schema(description = "Old location id")
    private int oldPlace;

    @Schema(description = "Old Location Qtys")
    private int qty;

    @Schema(description = "New Location Qtys")
    private int newPlaceQty;

    @Schema(description = "Costs")
    private double cost;

    @Schema(description = "Other Qtys")
    private double otherQty;

    public double getOtherQty() {
        return otherQty;
    }

    public double getCost() {
        return cost;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int assetId) {
        this.productId = assetId;
    }

    public int getNewPlace() {
        return newPlace;
    }

    public void setNewPlace(int newPlace) {
        this.newPlace = newPlace;
    }

    public int getOldPlace() {
        return oldPlace;
    }

    public void setOldPlace(int oldPlace) {
        this.oldPlace = oldPlace;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getNewPlaceQty() {
        return newPlaceQty;
    }
}
