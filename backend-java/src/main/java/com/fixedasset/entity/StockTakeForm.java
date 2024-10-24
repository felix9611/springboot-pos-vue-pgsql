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

@Component
@Data
@TableName("stocktake_form")
public class StockTakeForm {
    @Schema(description = "The data id, Only apply for response and update apis")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "The stock take action name")
    @TableField("name")
    private String name;

    @Schema(description = "Remark")
    @TableField("remark")
    private String remark;

    @Schema(description = "Stock take location id")
    @TableField("location_id")
    private int locationId;

    @Schema(description = "Start date time stamp, apply for response only")
    @TableField("start_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime startTime;

    @Schema(description = "End date time stamp, apply for response only")
    @TableField("end_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime endTime;

    @Schema(description = "Stock take status, 0 = Void, 1 = Active, 2 = Finished")
    @TableField("status")
    private int status;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int page = 1;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int limit = 10;
}
