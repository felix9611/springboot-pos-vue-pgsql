package com.fixedasset.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.util.List;
@Data
@TableName("product_list")
@Component
public class ProductList extends BaseEntity {

    @Schema(description = "The product code")
    @TableField("product_code")
    private String productCode;

    @Schema(description = "The product name")
    @TableField("product_name")
    private String productName;

    @Schema(description = "The item code")
    @TableField("item_code")
    private String itemCode;

    @Schema(description = "The brand code")
    @TableField("brand_name")
    private String brandName;

    @Schema(description = "The product type data id")
    @TableField("type_id")
    private int typeId;

    @Schema(description = "Unit")
    @TableField("unit")
    private String unit;

    @Schema(description = "Product Description")
    @TableField("description")
    private String description;

    @Schema(description = "Product Cost Price from Vendor")
    @TableField("cost_price")
    private Double costPrice;

    @Schema(description = "Product Retail Price")
    @TableField("retail_price")
    private Double retailPrice;

    @Schema(description = "The DEPARTMENT data id")
    @TableField("dept_id")
    private int deptId;

    @Schema(description = "The Vendor data id")
    @TableField("vendor_id")
    private int vendorId;

    @Schema(description = "1 = Taxable, 0 Non Taxable")
    @TableField("tax")
    private int tax;

    @Schema(description = "Sales Tax/VAT code")
    @TableField("tax_code")
    private String taxCode;

    @Schema(description = "Plus Sales Tax/VAT Amount")
    @TableField("tax_rate")
    private Double taxRate;

    @Schema(description = "Plus Sales Tax/VAT Amount")
    @TableField("after_tax")
    private Double afterTax;

    @Schema(description = "Sales Tax/VAT Amount")
    @TableField("tax_amount")
    private Double taxAmount;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int page = 1;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int limit = 10;

    @Schema(description = "Only apply for response for add product files")
    @TableField(exist = false)
    private List<ProductListFile> productListFiles;

    @Schema(description = "Only apply for response for add product files")
    @TableField(exist = false)
    private List<ProductListFile> newProductListFiles;

}
