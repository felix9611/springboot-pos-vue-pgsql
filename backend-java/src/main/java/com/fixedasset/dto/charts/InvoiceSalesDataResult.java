package com.fixedasset.dto.charts;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

@EqualsAndHashCode()
@Data
public class InvoiceSalesDataResult {
    @Transient private String placeName;
    @Transient private String productName;
    @Transient private String typeName;
    @Transient private String deptName;
    @Transient private double totalPrice;
    @Transient private int counts;
}
