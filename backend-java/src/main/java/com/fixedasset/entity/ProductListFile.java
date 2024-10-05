package com.fixedasset.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@TableName("product_list_file")
@Component
public class ProductListFile extends BaseEntity{
    @TableField("product_id")
    private int productId;

    @TableField("file_name")
    private String fileName;

    @TableField("base64")
    private String base64;
}
