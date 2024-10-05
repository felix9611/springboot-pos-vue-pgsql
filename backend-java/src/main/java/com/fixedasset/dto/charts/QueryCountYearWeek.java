package com.fixedasset.dto.charts;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

@EqualsAndHashCode()
@Data
public class QueryCountYearWeek {
    @Transient private String yearWeek;
    @Transient private int count;
}
