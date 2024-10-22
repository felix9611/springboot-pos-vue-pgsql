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
@TableName("action_record")
public class ActionRecord {
    @TableId(value = "id", type = IdType.AUTO)
    @Schema(description = "The data id, Only apply for response")
    private Long id;

    @Schema(description = "Action Name")
    @TableField("action_name")
    private String actionName;

    @Schema(description = "Action Method")
    @TableField("action_method")
    private String actionMethod;

    @Schema(description = "Action From")
    @TableField("action_from")
    private String actionFrom;

    @Schema(description = "Action Date Stamp")
    @TableField("action_data")
    private String actionData;

    @Schema(description = "Action Status")
    @TableField("action_success")
    private String actionSuccess;

    @Schema(description = "Action Time Stamp")
    private OffsetDateTime created;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int page;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int limit;
}
