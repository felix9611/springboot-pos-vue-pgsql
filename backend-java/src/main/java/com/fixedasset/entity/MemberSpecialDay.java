package com.fixedasset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Data
@TableName("member_special_day")
@Component
public class MemberSpecialDay {
    @Schema(description = "The data id, Only apply for response and update apis")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "The member data id")
    @TableField("member_id")
    private int memberId;

    @Schema(description = "The special date name")
    @TableField("name")
    private String name;

    @Schema(description = "The special date")
    @TableField("date")
    private String date;

    @Schema(description = "remark")
    @TableField("remark")
    private String remark;

    @Schema(description = "The created time stamp, only apply for respone")
    @TableField("created_at")
    private OffsetDateTime createdAt;

    @Schema(description = "The updated time stamp, only apply for respone")
    @TableField("updated_at")
    private OffsetDateTime updatedAt;

    @Schema(description = "1 = active, 0 = inactive")
    @TableField("status")
    private int status;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int page = 1;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int limit = 10;
}
