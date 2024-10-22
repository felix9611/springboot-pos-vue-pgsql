package com.fixedasset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@TableName("invoice_item")
@Component
public class InvoiceItem {
    @Schema(description = "The data id, Only apply for response and update apis")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "The invoice data id")
    @TableField("invoice_id")
    private int invoiceId;

    @Schema(description = "The product data id")
    @TableField("product_id")
    private int productId;

    @Schema(description = "The sales qty")
    @TableField("qty")
    private int qty;

    @Schema(description = "The sales total price")
    @TableField("price")
    private Double price;

    @Schema(description = "The discount amount/number")
    @TableField("discount")
    private Double discount;

    @Schema(description = "The discount type")
    @TableField("discount_type")
    private String discountType;

    @Schema(description = "The TAX TYPE")
    @TableField("tax_type")
    private String taxType;

    @Schema(description = "The sales tax/VAT code")
    @TableField("tax_code")
    private String taxCode;

    @Schema(description = "The sales tax/VAT Rate")
    @TableField("tax_rate")
    private Double taxRate;

    @Schema(description = "The taxable amount")
    @TableField("tax_amount")
    private Double taxAmount;
}
