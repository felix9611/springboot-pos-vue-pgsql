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
@TableName("payment")
@Component
public class Payment {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("invoice_id")
    private int invoiceId;
    @TableField("method")
    private String method;
    @TableField("amount")
    private Double amount;
    @TableField("payment_time")
    private OffsetDateTime paymentTime;
}
