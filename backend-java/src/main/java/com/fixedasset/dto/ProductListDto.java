package com.fixedasset.dto;

import com.fixedasset.entity.ProductList;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductListDto extends ProductList {
    @Transient private String typeCode;

    @Transient private String typeName;
}
