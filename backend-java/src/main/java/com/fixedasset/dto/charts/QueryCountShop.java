package com.fixedasset.dto.charts;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

@EqualsAndHashCode()
@Data
public class QueryCountShop {
    @Transient
    private String placeName;
    @Transient private int count;
}
