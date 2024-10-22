package com.fixedasset.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

import javax.persistence.Transient;

import io.swagger.v3.oas.annotations.media.Schema;

@EqualsAndHashCode()
@Data
public class ProductListUploadDto {

    @Schema(description = "The product code")
    @Transient private String productCode;

    @Schema(description = "The product name")
    @Transient private String productName;

    @Schema(description = "The item code")
    @Transient private String itemCode;

    @Schema(description = "The brand code")
    @Transient private String brandName;

    @Schema(description = "The type code")
    @Transient private String typeCode;

    @Schema(description = "The type name")
    @Transient private String typeName;

    @Schema(description = "Unit")
    @Transient private String unit;

    @Schema(description = "Product Description")
    @Transient private String description;

    @Schema(description = "Product Retail Price")
    @Transient private double retailPrice;

    @Schema(description = "The department code")
    @Transient private String departmentCode;

    @Schema(description = "The department name")
    @Transient private String departmentName;

    @Schema(description = "The vendor code")
    @Transient private String vendorCode;

    @Schema(description = "The vendor name")
    @Transient private String vendorName;

    @Schema(description = "The vendor other name")
    @Transient private String vendorOtherName;

    @Schema(description = "Vendor Type")
    @Transient private String vendorType;

    @Schema(description = "Vendor Email")
    @Transient private String vendorEmail;

    @Schema(description = "Vendor Phone")
    @Transient private String vendorPhone;

    @Schema(description = "Vendor Fax")
    @Transient private String vendorFax;

    @Schema(description = "Vendor Address")
    @Transient private String vendorAddress;

    @Schema(description = "Vendor Contact Person")
    @Transient private String vendorContactPerson;

    @Schema(description = "Vendor Remark")
    @Transient private String vendorRemark;

    @Schema(description = "1 = Taxable, 0 Non Taxable")
    @Transient private int tax;

    @Schema(description = "Sales Tax/VAT code")
    @Transient private String taxCode;

    @Schema(description = "Plus Sales Tax/VAT Amount")
    @Transient private double taxRate;

    @Schema(description = "Plus Sales Tax/VAT Amount")
    @Transient private double afterTax;

    @Schema(description = "Sales Tax/VAT Amount")
    @Transient private double taxAmount;

    @Schema(description = "Product Location List")
    @Transient List<ProductLocationUploadDto> productLocations;
}
