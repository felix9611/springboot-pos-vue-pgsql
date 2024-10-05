package com.fixedasset.dto.charts;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

@EqualsAndHashCode()
@Data
public class QueryTotalYearWeek {
    @Transient private String yearWeek;
    @Transient private int total;
}
