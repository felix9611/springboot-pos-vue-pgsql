package com.fixedasset.dto;

import lombok.Data;

@Data
public class ProductLocationChangeDto {
    private int productId;
    private int newPlace;
    private int oldPlace;
    private int qty;
    private int newPlaceQty;
    private int cost;
    private int otherQty;

    public int getOtherQty() {
        return otherQty;
    }

    public int getCost() {
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
