package com.fixedasset.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@TableName("product_list_file")
@Component
public class ProductListFile extends BaseEntity{
    @Schema(description = "The product data id")
    @TableField("product_id")
    private int productId;

    @Schema(description = "Uploaded file name")
    @TableField("file_name")
    private String fileName;

    @Schema(description = "File content in base64")
    @TableField("base64")
    private String base64;
}
