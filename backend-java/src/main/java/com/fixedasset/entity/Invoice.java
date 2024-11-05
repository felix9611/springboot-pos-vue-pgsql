package com.fixedasset.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fixedasset.dto.InvoiceItemListDto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@TableName("invoice")
@Component
public class Invoice {
    @Schema(description = "The data id, Only apply for response and update apis")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "Invoice No.")
    @TableField("number")
    private String number;

    @Schema(description = "Member data id")
    @TableField("member_id")
    private int memberId;

    @Schema(description = "Sales Total amount")
    @TableField("total_amount")
    private Double totalAmount;

    @Schema(description = "Sales discount")
    @TableField("discount")
    private Double discount;

    @Schema(description = "Sales discount Type")
    @TableField("discount_type")
    private String discountType;

    @Schema(description = "Sales Location data id")
    @TableField("location_id")
    private int locationId;

    @Schema(description = "Sales Tax/VAT total amount")
    @TableField("tax_total")
    private Double taxTotal;

    @Schema(description = "Sales Tax/VAT reference number")
    @TableField("tax_ref_no")
    private String taxRefNo;

    @Schema(description = "0 = active, 1 = void, 2 = have refund")
    @TableField("void_num")
    private int voidNum;

    @Schema(description = "Created date time, Only apply for response")
    @TableField("created_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime createdAt;

    @Schema(description = "Void date time, Only apply for response")
    @TableField("void_at")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private OffsetDateTime voidAt;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int page = 1;

    @Schema(description = "Only apply for paging in list api")
    @TableField(exist = false)
    private int limit = 10;

    @Schema(description = "Only apply for paging in list api, date range to")
    @TableField(exist = false)
    private OffsetDateTime dateTo;

    @Schema(description = "Only apply for paging in list api, date range from")
    @TableField(exist = false)
    private OffsetDateTime dateFrom;

    @Schema(description = "Only apply for response only, invoice po items")
    @TableField(exist = false)
    private List<InvoiceItemListDto> invoiceItems;
    
    @Schema(description = "Only apply for request only, invoice po items to add")
    @TableField(exist = false)
    private List<InvoiceItem> newInvoiceItems;

    @Schema(description = "Only apply for response only, invoice payments")
    @TableField(exist = false)
    private List<Payment> paymentItems;

    @Schema(description = "Only apply for request only, invoice payments to add")
    @TableField(exist = false)
    private List<Payment> newPaymentItems;

}