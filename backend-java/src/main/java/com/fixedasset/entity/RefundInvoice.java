package com.fixedasset.entity;

import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fixedasset.dto.RefundInvoiceItemList;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Component
@Data
@TableName("refund_invoice")
public class RefundInvoice {

    @Schema(description = "The data id, Only apply for response and update apis")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "Invoice data id")
    @TableField("invoice_id")
    private int invoiceId;

    @Schema(description = "Case number")
    @TableField("case_number")
    private String caseNo;

    @Schema(description = "0 = No return, 1 = have return items")
    @TableField("items_return")
    private int itemsReturn;

    @Schema(description = "Item return datetime")
    @TableField("return_date")
    private OffsetDateTime returnDate;

    @Schema(description = "Return code")
    @TableField("return_code")
    private String returnCode;

    @Schema(description = "Return reason")
    @TableField("return_reason")
    private String returnReason;

    @Schema(description = "0 = No refund, 1 = have refund")
    @TableField("refund")
    private int refund;

    @Schema(description = "Refund Amount")
    @TableField("refund_amount")
    private Double refundAmount;

    @Schema(description = "Refund method")
    @TableField("refund_method")
    private String refundMethod;

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

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int page = 1;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int limit = 10;

    @Schema(description = "Only apply for response only, invoice items for return")
    @TableField(exist = false)
    private List<RefundInvoiceItemList> refundInvoiceItems;

    @Schema(description = "Only apply for request only, invoice items for return")
    @TableField(exist = false)
    private List<RefundInvoiceItem> newRefundInvoiceItems;
    
}
