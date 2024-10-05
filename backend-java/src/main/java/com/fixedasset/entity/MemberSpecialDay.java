package com.fixedasset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@TableName("member_special_day")
@Component
public class MemberSpecialDay {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("member_id")
    private int memberId;

    @TableField("name")
    private String name;

    @TableField("date")
    private OffsetDateTime date;

    @TableField("remark")
    private String remark;

    @TableField("created_at")
    private OffsetDateTime createdAt;

    @TableField("updated_at")
    private OffsetDateTime updatedAt;

    @TableField("status")
    private int status;

    @TableField(exist = false)
    private int page = 1;

    @TableField(exist = false)
    private int limit = 10;
}
