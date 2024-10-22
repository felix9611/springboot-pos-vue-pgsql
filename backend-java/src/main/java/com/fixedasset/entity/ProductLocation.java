package com.fixedasset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@TableName("product_location")
@Component
public class ProductLocation {
    @Schema(description = "The data id, Only apply for response and update apis")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "The product data id")
    @TableField("product_id")
    private int productId;

    @Schema(description = "The location data id")
    @TableField("location_id")
    private int locationId;

    @Schema(description = "Total qty")
    @TableField("qty")
    private int qty;

    @Schema(description = "Total price")
    @TableField("total_price")
    private double totalPrice;

    @Schema(description = "For request only, if case Total cost")
    @TableField(exist = false)
    private int cost;

    @Schema(description = "For request only, if case other qty")
    @TableField(exist = false)
    private int otherQty;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int page = 1;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int limit = 10;
}
