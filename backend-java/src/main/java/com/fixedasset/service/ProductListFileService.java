package com.fixedasset.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fixedasset.entity.ProductListFile;

import java.util.List;

public interface ProductListFileService extends IService<ProductListFile> {
    void saveListPicture(ProductListFile productListFile);
    List<ProductListFile> getByAssetId(ProductListFile productListFile);
    void removeFile(Long id);
}
