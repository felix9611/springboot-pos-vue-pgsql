package com.fixedasset.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("location")
public class Location extends BaseEntity {
    @Schema(description = "The location code")
    @TableField("place_code")
    private String placeCode;

    @Schema(description = "The location name")
    @TableField("place_name")
    private String placeName;

    @Schema(description = "The location other name")
    @TableField("place_other_name")
    private String placeOtherName;

    @Schema(description = "The location country code")
    @TableField("country")
    private String country;

    @Schema(description = "The location address")
    @TableField("address")
    private String address;

    @Schema(description = "The location zip code")
    @TableField("zip_code")
    private String zipCode;

    @Schema(description = "The location address")
    @TableField("email")
    private String email;

    @Schema(description = "The location phone number")
    @TableField("phone")
    private String phone;

    @Schema(description = "The location zip code")
    @TableField("fax")
    private String fax;

    @Schema(description = "The remark")
    @TableField("remark")
    private String remark;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int page = 1;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int limit = 10;
    
    @Schema(description = "The search")
    @TableField(exist = false)
    private String search;
}
