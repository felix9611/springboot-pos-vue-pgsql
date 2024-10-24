package com.fixedasset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;
import java.time.OffsetDateTime;

@Data
@TableName("invrecord")
@Component
public class InvRecord {
    @Schema(description = "The data id, Only apply for response and update apis")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "Qtys")
    @TableField("qty")
    private int qty;

    @Schema(description = "The product data id")
    @TableField("product_id")
    private int productId;

    @Schema(description = "The location from (data id)")
    @TableField("loc_From")
    private int locFrom;

    @Schema(description = "The location to (data id)")
    @TableField("loc_to")
    private int locTo;

    @Schema(description = "The total cost")
    @TableField("cost")
    private double cost;

    @Schema(description = "The staff id")
    @TableField("staff")
    private String staff;

    @Schema(description = "The date time stamp, only apply for response")
    @TableField("time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime timeAt;

    @Schema(description = "Only apply for paging in list api, the date time range from")
    @TableField(exist = false)
    private OffsetDateTime timeAtFrom;

    @Schema(description = "Only apply for paging in list api, the date time range to")
    @TableField(exist = false)
    private OffsetDateTime timeAtTo;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int page = 1;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int limit = 10;
}
