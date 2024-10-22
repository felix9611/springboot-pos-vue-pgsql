package com.fixedasset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;

@Component
@Data
@TableName("stocktake_detail")
public class StockTakeDetail {
    @Schema(description = "The data id, Only apply for response and update apis")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "Stock take form id")
    @TableField("form_id")
    private int formId;

    @Schema(description = "Product id")
    @TableField("product_id")
    private int productId;

    @Schema(description = "Location id")
    @TableField("location_id")
    private int locationId;

    @Schema(description = "Qtys")
    @TableField("qty")
    private int qty;

    @Schema(description = "Date Time stamp, apply for response only")
    @TableField("time_at")
    private OffsetDateTime timeAt;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int page = 1;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int limit = 10;
}
