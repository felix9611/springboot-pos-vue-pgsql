package com.fixedasset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Component
@Data
@TableName("stocktake_detail")
public class StockTakeDetail {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("form_id")
    private int formId;
    @TableField("product_id")
    private int productId;
    @TableField("location_id")
    private int locationId;
    @TableField("qty")
    private int qty;
    @TableField("time_at")
    private OffsetDateTime timeAt;

    @TableField(exist = false)
    private int page = 1;

    @TableField(exist = false)
    private int limit = 10;
}
