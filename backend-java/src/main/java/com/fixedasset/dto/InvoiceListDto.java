package com.fixedasset.dto;

import com.fixedasset.entity.Invoice;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Transient;

@EqualsAndHashCode(callSuper = true)
@Data
public class InvoiceListDto extends Invoice {
    @Transient private String memberName;
    @Transient private String memberPhone;
    @Transient private String memberAddress;
    @Transient private String mcName;
    @Transient private String placeCode;
    @Transient private String placeName;
    @Transient private String zipCode;
    @Transient private String address;
    @Transient private String country;
}
