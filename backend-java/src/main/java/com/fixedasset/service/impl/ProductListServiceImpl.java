package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.dto.ProductListDto;
import com.fixedasset.dto.ProductLocationListDto;
import com.fixedasset.entity.ActionRecord;
import com.fixedasset.entity.ProductList;
import com.fixedasset.entity.ProductListFile;
import com.fixedasset.entity.ProductLocation;
import com.fixedasset.entity.ProductType;
import com.fixedasset.mapper.ActionRecordMapper;
import com.fixedasset.mapper.ProductListMapper;
import com.fixedasset.service.ProductListFileService;
import com.fixedasset.service.ProductListService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ProductListServiceImpl extends ServiceImpl<ProductListMapper, ProductList> implements ProductListService {

    @Resource private ProductListMapper productListMapper;

    @Resource private ActionRecordMapper actionRecordMapper;

    @Resource private ActionRecord actionRecord;

    @Resource private ProductList productList;

    @Resource private ProductListFileService productListFileService;

    @Resource private ProductListFile productListFile;

    public void createOne(ProductList productList) {
        String newCode = getNewCode();
        productList.setProductCode(newCode);
        productList.setStatu(1);
        productList.setCreated(OffsetDateTime.now());
        productListMapper.insert(productList);

        actionRecord.setActionName("Create");
        actionRecord.setActionMethod("POST");
        actionRecord.setActionFrom("Product List");
        actionRecord.setActionData(productList.toString());
        actionRecord.setActionSuccess("Success");
        actionRecord.setCreated(OffsetDateTime.now());
     //   createdAction(actionRecord);
    }

    public void voidOne(Long id) {
        productList.setId(id);
        productList.setStatu(0);
        productListMapper.updateById(productList);

        List<ProductListFile> newProductListFiles = productList.getNewProductListFiles();

        if (newProductListFiles.size() > 0) {
            for (ProductListFile productListFile : newProductListFiles) {
                productListFile.setProductId(Math.toIntExact(productList.getId()));
                productListFileService.saveListPicture(productListFile);
            }
        }

        actionRecord.setActionName("Remove");
        actionRecord.setActionMethod("DELETE");
        actionRecord.setActionFrom("Product List");
        actionRecord.setActionData(productList.toString());
        actionRecord.setActionSuccess("Success");
        actionRecord.setCreated(OffsetDateTime.now());
        createdAction(actionRecord);
    }

    public void updateOne(ProductList productList) {
        productList.setUpdated(OffsetDateTime.now());
        productListMapper.updateById(productList);

        List<ProductListFile> newProductListFiles = productList.getNewProductListFiles();

        if (newProductListFiles.size() > 0) {
            for (ProductListFile productListFile : newProductListFiles) {
                productListFile.setProductId(Math.toIntExact(productList.getId()));
                productListFileService.saveListPicture(productListFile);
            }
        }

        actionRecord.setActionName("Update");
        actionRecord.setActionMethod("POST");
        actionRecord.setActionFrom("Product List");
        actionRecord.setActionData(productList.toString());
        actionRecord.setActionSuccess("Success");
        actionRecord.setCreated(OffsetDateTime.now());
     //   createdAction(actionRecord);
    }

    public ProductList findOne(ProductList productList) {
        LambdaQueryWrapper<ProductList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductList::getProductCode, productList.getProductCode());
        queryWrapper.eq(ProductList::getStatu, 1);
        return productListMapper.selectOne(queryWrapper);
    }

    public ProductList findOneById(Long id) {
        LambdaQueryWrapper<ProductList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductList::getId, id);
        queryWrapper.eq(ProductList::getStatu, 1);
        ProductList one = productListMapper.selectOne(queryWrapper);

        productListFile.setProductId(Math.toIntExact(id));
        List<ProductListFile> files = productListFileService.getByAssetId(productListFile);
        one.setProductListFiles(files);

        return one;
    }

    public Page<ProductListDto> newPage(Page page, LambdaQueryWrapper<ProductList> queryWrapper) {
        return productListMapper.page(page, queryWrapper);
    }

    public List<ProductListDto> listAll(LambdaQueryWrapper<ProductList> queryWrapper) {
        return productListMapper.listAll(queryWrapper);
    }

    public int createdAction(ActionRecord actionRecord) {
        return actionRecordMapper.insert(actionRecord);
    }

    public String getNewCode() {
        LambdaQueryWrapper<ProductList> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.select(ProductList::getProductCode);

        List<Object> productCodes = productListMapper.selectObjs(lambdaQueryWrapper);
        AtomicReference<Integer> maxCodes = new AtomicReference<>(0);

        productCodes.forEach(o -> {
            String code = String.valueOf(o);
            if (code.length() >= 6) {
                Integer one = Integer.parseInt(code.substring(code.length() - 5));
                if (one > maxCodes.get()) {
                    maxCodes.set(one);
                }
            }

        });
        return padRight(maxCodes.get() + 1, 6, "0");
    }

    public static String padRight(int oriStr, int len, String alexi) {
        StringBuilder str = new StringBuilder();
        int strlen = String.valueOf(oriStr).length();
        if (strlen < len) {
            for (int i = 0; i < len - strlen; i++) {
                str.append(alexi);
            }
        }
        str.append(oriStr);
        return str.toString();
    }

}
