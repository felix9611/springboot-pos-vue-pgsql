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
@TableName("payment")
@Component
public class Payment {
    @Schema(description = "The data id, Only apply for response and update apis")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "The invoice data id")
    @TableField("invoice_id")
    private int invoiceId;

    @Schema(description = "The payment method")
    @TableField("method")
    private String method;

    @Schema(description = "The payment amount")
    @TableField("amount")
    private Double amount;

    @Schema(description = "The payment time stamp")
    @TableField("payment_time")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime paymentTime;
}
