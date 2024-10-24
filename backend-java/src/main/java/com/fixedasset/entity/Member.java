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
import java.util.List;

@Data
@TableName("member")
@Component
public class Member {
    @Schema(description = "The data id, Only apply for response and update apis")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "The member name")
    @TableField("name")
    private String name;

    @Schema(description = "The member mail address")
    @TableField("address")
    private String address;

    @Schema(description = "The member phone number")
    @TableField("phone")
    private String phone;

    @Schema(description = "The member email address")
    @TableField("email")
    private String email;

    @Schema(description = "The member fax number")
    @TableField("fax")
    private String fax;

    @Schema(description = "The member class")
    @TableField("class")
    private int classes;

    @Schema(description = "The created time stamp, only apply for respone")
    @TableField("created_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime createdAt;

    @Schema(description = "The updated time stamp, only apply for respone")
    @TableField("updated_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
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

    @Schema(description = "The member special days list")
    @TableField(exist = false)
    private List<MemberSpecialDay> memberSpecialDays;

    @Schema(description = "For request only, if case member class name")
    @TableField(exist = false)
    private String className;
}