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
@TableName("invoice")
@Component
public class Invoice {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("number")
    private String number;

    @TableField("member_id")
    private int memberId;

    @TableField("total_amount")
    private Double totalAmount;

    @TableField("discount")
    private Double discount;

    @TableField("discount_type")
    private String discountType;

    @TableField("location_id")
    private int locationId;

    @TableField("tax_total")
    private Double taxTotal;

    @TableField("tax_ref_no")
    private String taxRefNo;

    @TableField("void")
    private int voidNum;

    @TableField("created_at")
    private OffsetDateTime createdAt;

    @TableField("void_at")
    private OffsetDateTime voidAt;

    @TableField(exist = false)
    private int page = 1;

    @TableField(exist = false)
    private int limit = 10;

    @TableField(exist = false)
    private OffsetDateTime dateTo;

    @TableField(exist = false)
    private OffsetDateTime dateFrom;

}
