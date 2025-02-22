package com.fixedasset.entity;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("promotion_type")
@Component
public class PromotionType extends BaseEntity {

    @Schema(description = "The promotion id")
    @TableField("promotion_id")
    private int promotionId;

    @Schema(description = "The type id")
    @TableField("type_id")
    private int typeId;

    @Schema(description = "The promotion name")
    @TableField("promotion_name")
    private String promotionName;

    @Schema(description = "The promotion code")
    @TableField("promotion_code")
    private String promotionCode;

    @Schema(description = "The discount amount")
    @TableField("discount_amount")
    private Double discountAmount;

    @Schema(description = "Discount type")
    @TableField("discount_type")
    private String discountType;

    @Schema(description = "For response only")
    @TableField(exist = false)
    private String typeCode;

    @Schema(description = "For response only")
    @TableField(exist = false)
    private String typeName;
}   
