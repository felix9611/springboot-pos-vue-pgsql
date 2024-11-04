package com.fixedasset.entity;

import java.time.OffsetDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.stereotype.Component;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import com.baomidou.mybatisplus.annotation.TableName;

@Component
@Data
@TableName("refund_invoice_item")
public class RefundInvoiceItem {
    @Schema(description = "The data id, Only apply for response and update apis")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "Refund Invoice data id")
    @TableField("refund_invoice_id")
    private int refundInvoiceId;

    @Schema(description = "Product data id")
    @TableField("product_id")
    private int productId;

    @Schema(description = "Refund Amount")
    @TableField("refund_amount")
    private Double refundAmount;

    @Schema(description = "Items status")
    @TableField("items_status")
    private String itemsStatus;

    @Schema(description = "Created date time, Only apply for response")
    @TableField("created_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime createdAt;

    @Schema(description = "Void date time, Only apply for response")
    @TableField("updated_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime updatedAt;

    @Schema(description = "0 = Inactive, 1 = active")
    @TableField("status")
    private int status;
}
