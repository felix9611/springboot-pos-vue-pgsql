package com.fixedasset.entity;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("promotion_catelog_type")
@Component
public class PromotionCatelogType extends BaseEntity {
    @Schema(description = "The promotion name")
    @TableField("catelog_code")
    private String catelogCode;
    @Schema(description = "The promotion name")
    @TableField("catelog_name")
    private String catelogName;

    @Schema(description = "Remark")
    @TableField("remark")
    private String remark;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int page = 1;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int limit = 10;

    @Schema(description = "Find by Name or code")
    @TableField(exist = false)
    private String name;
    
}
