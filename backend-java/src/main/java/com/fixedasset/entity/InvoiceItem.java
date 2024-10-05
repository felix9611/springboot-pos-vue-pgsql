package com.fixedasset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@TableName("invoice_item")
@Component
public class InvoiceItem {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("invoice_id")
    private int invoiceId;
    @TableField("product_id")
    private int productId;
    @TableField("qty")
    private int qty;
    @TableField("price")
    private Double price;
    @TableField("discount")
    private Double discount;
    @TableField("discount_type")
    private String discountType;
    @TableField("tax_type")
    private String taxType;
    @TableField("tax_code")
    private String taxCode;
    @TableField("tax_rate")
    private Double taxRate;
    @TableField("tax_amount")
    private Double taxAmount;
}
