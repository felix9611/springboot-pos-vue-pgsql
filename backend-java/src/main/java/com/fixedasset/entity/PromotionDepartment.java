package com.fixedasset.entity;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("promotion_department")
@Component
public class PromotionDepartment extends BaseEntity {

    @Schema(description = "The promotion id")
    @TableField("promotion_id")
    private int promotionId;

    @Schema(description = "The place id")
    @TableField("dept_id")
    private int deptId;

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

    @Schema(description = "The department code")
    @TableField(exist = false)
    private String deptIdCode;

    @Schema(description = "The epartment name")
    @TableField(exist = false)
    private String deptIdName;
}   
