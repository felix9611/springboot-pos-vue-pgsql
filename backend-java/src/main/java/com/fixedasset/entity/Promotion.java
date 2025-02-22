package com.fixedasset.entity;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("promotion")
@Component
public class Promotion extends BaseEntity {
    @Schema(description = "The promotion name")
    @TableField("promotion_name")
    private String promotionName;

    @Schema(description = "The promotion code")
    @TableField("promotion_code")
    private String promotionCode;

    @Schema(description = "The promotion code")
    @TableField("promotion_type")
    private String promotionType;

    @Schema(description = "The description")
    @TableField("description")
    private String description;

    @Schema(description = "The date time of period start")
    @TableField("period_start")
    private OffsetDateTime periodStart;

    @Schema(description = "The date time of period end")
    @TableField("period_end")
    private OffsetDateTime periodEnd;

    @Schema(description = "Online")
    @TableField("online")
    private int online;

    @Schema(description = "In Store")
    @TableField("in_store")
    private int inStore;

    @Schema(description = "Member")
    @TableField("member")
    private int member;

    @Schema(description = "After Before Tax")
    @TableField("after_before_tax")
    private int afterBeforeTax;

    @Schema(description = "Manager staff id")
    @TableField("manager")
    private int manager;

    @Schema(description = "Discount amount or %")
    @TableField("discount")
    private Double discount;

    @Schema(description = "Discount type")
    @TableField("discount_type")
    private String discountType;

    @Schema(description = "All to use one discount")
    @TableField("all_one_discount")
    private int allOneDiscount;

    @Schema(description = "Coupon Requested or not")
    @TableField("coupon_request")
    private int couponRequest;

    @Schema(description = "Coupon Main Code")
    @TableField("coupon_main_code")
    private String couponMainCode;

    @Schema(description = "Remark")
    @TableField("remark")
    private String remark;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int page = 1;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int limit = 10;

    @Schema(description = "Find by Name or code")
    @TableField(exist = false)
    private String name;

    @Schema(description = "Only apply for response only, promotion department items")
    @TableField(exist = false)
    private List<PromotionDepartment> promotionDepartmentItems;

    @Schema(description = "Only apply for request only,, promotion department items")
    @TableField(exist = false)
    private List<PromotionDepartment> newPromotionDepartmentItems;

    @Schema(description = "Only apply for response only, promotion type items")
    @TableField(exist = false)
    private List<PromotionType> promotionTypeItems;

    @Schema(description = "Only apply for request only,, promotion type items")
    @TableField(exist = false)
    private List<PromotionType> newPromotionTypeItems;

    @Schema(description = "Only apply for response only, promotion Location items")
    @TableField(exist = false)
    private List<PromotionLocation> promotionLocationItems;

    @Schema(description = "Only apply for request only,, promotion Location items")
    @TableField(exist = false)
    private List<PromotionLocation> newPromotionLocationItems;
}   


