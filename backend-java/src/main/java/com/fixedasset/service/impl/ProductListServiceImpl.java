package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.dto.ProductListDto;
import com.fixedasset.dto.ProductListUploadDto;
import com.fixedasset.dto.ProductLocationListDto;
import com.fixedasset.dto.ProductLocationUploadDto;
import com.fixedasset.entity.ActionRecord;
import com.fixedasset.entity.Department;
import com.fixedasset.entity.InvRecord;
import com.fixedasset.entity.Location;
import com.fixedasset.entity.ProductList;
import com.fixedasset.entity.ProductListFile;
import com.fixedasset.entity.ProductLocation;
import com.fixedasset.entity.ProductType;
import com.fixedasset.entity.Vendor;
import com.fixedasset.mapper.ActionRecordMapper;
import com.fixedasset.mapper.ProductListMapper;
import com.fixedasset.mapper.ProductLocationMapper;
import com.fixedasset.service.ActionRecordService;
import com.fixedasset.service.DepartmentService;
import com.fixedasset.service.InvRecordService;
import com.fixedasset.service.LocationService;
import com.fixedasset.service.ProductListFileService;
import com.fixedasset.service.ProductListService;
import com.fixedasset.service.ProductLocationService;
import com.fixedasset.service.ProductTypeService;
import com.fixedasset.service.VendorService;

import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ProductListServiceImpl extends ServiceImpl<ProductListMapper, ProductList> implements ProductListService {

    @Resource private ProductListMapper productListMapper;

    @Resource private ActionRecordService actionRecordService;

    @Resource private ProductList productList;

    @Resource private ProductListFileService productListFileService;

    @Resource private ProductListFile productListFile;

    @Resource private ProductTypeService productTypeService;

    @Resource private LocationService locationService;

    @Resource private DepartmentService departmentService;

    @Resource private VendorService vendorService;

    @Resource private ProductLocation productLocation;

    @Resource private ProductLocationService productLocationService;

    @Resource private ProductLocationMapper productLocationMapper;

    @Resource private InvRecord invRecord;

    @Resource private InvRecordService invRecordService;

    public void importData(List<ProductListUploadDto> productListUploads) {
        for (ProductListUploadDto productListUpload : productListUploads) {

                LambdaQueryWrapper<ProductList> queryWrapperCheckCode = Wrappers.lambdaQuery();
                queryWrapperCheckCode.eq(ProductList::getProductCode, productListUpload.getProductCode());
                queryWrapperCheckCode.eq(ProductList::getStatu, 1);
                ProductList checkCode = productListMapper.selectOne(queryWrapperCheckCode);

                if (checkCode == null) {
                    productList.setProductCode(productListUpload.getProductCode());
                } else {
                    String newCode = getNewCode();
                    productList.setProductCode(newCode);
                }
                
                productList.setProductName(productListUpload.getProductName());
                productList.setItemCode(productListUpload.getItemCode());
                productList.setBrandName(productListUpload.getBrandName());
                productList.setUnit(productListUpload.getUnit());
                productList.setDescription(productListUpload.getDescription());
                productList.setRetailPrice(productListUpload.getRetailPrice());
                productList.setTax(productListUpload.getTax());
                productList.setTaxRate(productListUpload.getTaxRate());
                productList.setTaxCode(productListUpload.getTaxCode());
                productList.setAfterTax(productListUpload.getAfterTax());
                productList.setTaxAmount(productListUpload.getTaxAmount());


                productList.setStatu(1);

                if (StringUtils.isNotBlank(productListUpload.getTypeCode()) || StringUtils.isNotBlank(productListUpload.getTypeName())) {
                    LambdaQueryWrapper<ProductType> queryWrapperType = Wrappers.lambdaQuery();
                    if (StringUtils.isNotBlank(productListUpload.getTypeCode()) ) {
                        queryWrapperType.eq(ProductType::getTypeCode, productListUpload.getTypeCode());
                    }
                    if (StringUtils.isNotBlank(productListUpload.getTypeName()) ) {
                        queryWrapperType.eq(ProductType::getTypeName, productListUpload.getTypeName());
                    }
                    ProductType productType = productTypeService.getOne(queryWrapperType);

                    if (productType == null) {
                        throw new RuntimeException("No product type active data in records!");
                    } else {
                        productList.setTypeId(Math.toIntExact(productType.getId()));
                    }
                }

                if (StringUtils.isNotBlank(productListUpload.getDepartmentCode()) || StringUtils.isNotBlank(productListUpload.getDepartmentName())) {
                    LambdaQueryWrapper<Department> queryWrapperDepartment = Wrappers.lambdaQuery();
                    if (StringUtils.isNotBlank(productListUpload.getDepartmentCode()) ) {
                        queryWrapperDepartment.eq(Department::getDeptCode, productListUpload.getDepartmentCode());
                    }
                    if (StringUtils.isNotBlank(productListUpload.getDepartmentName()) ) {
                        queryWrapperDepartment.eq(Department::getDeptName, productListUpload.getDepartmentName());
                    }
                    Department department = departmentService.getOne(queryWrapperDepartment);

                    if (department == null) {
                        throw new RuntimeException("No product type active data in records!");
                    } else {
                        productList.setDeptId(Math.toIntExact(department.getId()));
                    }
                }

                Vendor vendor = new Vendor();
                vendor.setVendorCode(productListUpload.getVendorCode());
                vendor.setVendorName(productListUpload.getVendorName());
                vendor.setVendorOtherName(productListUpload.getVendorOtherName());
                vendor.setType(productListUpload.getVendorType());
                vendor.setEmail(productListUpload.getVendorEmail());
                vendor.setPhone(productListUpload.getVendorPhone());
                vendor.setFax(productListUpload.getVendorFax());
                vendor.setAddress(productListUpload.getVendorAddress());
                vendor.setContactPerson(productListUpload.getVendorContactPerson());
                vendor.setRemark(productListUpload.getVendorRemark());

                if (StringUtils.isNotBlank(productListUpload.getVendorCode()) || StringUtils.isNotBlank(productListUpload.getVendorName())) {
                    LambdaQueryWrapper<Vendor> queryWrapperVendor = Wrappers.lambdaQuery();
                    if (StringUtils.isNotBlank(productListUpload.getVendorCode())) {
                        queryWrapperVendor.eq(Vendor::getVendorCode, productListUpload.getVendorCode());
                    }
                    if (StringUtils.isNotBlank(productListUpload.getVendorName())) {
                        queryWrapperVendor.eq(Vendor::getVendorName, productListUpload.getVendorName());
                    }
                    if (StringUtils.isNotBlank(productListUpload.getVendorType())) {
                        queryWrapperVendor.eq(Vendor::getType, productListUpload.getVendorType());
                    }
                    queryWrapperVendor.eq(Vendor::getStatu, 1);
                    Vendor checkOne = vendorService.getOne(queryWrapperVendor);

                    if (checkOne == null) {
                        vendor.setCreated(OffsetDateTime.now());
                        vendorService.createOne(vendor);
                        productList.setVendorId(Math.toIntExact(vendor.getId()));

                        
                    } else {
                        
                        productList.setVendorId(Math.toIntExact(checkOne.getId()));
                        vendor.setId(checkOne.getId());
                        vendor.setUpdated(OffsetDateTime.now());
                        vendor.setStatu(1);
                        vendorService.updateOne(vendor);
                    }

                    LambdaQueryWrapper<ProductList> queryWrapper = Wrappers.lambdaQuery();
                    queryWrapper.eq(ProductList::getProductName, productListUpload.getProductName());
                    queryWrapper.eq(ProductList::getStatu, 1);
                    ProductList checkProduct = productListMapper.selectOne(queryWrapper);

                    if (checkProduct == null) {
                        productList.setCreated(OffsetDateTime.now());
                        productList.setStatu(1);
                        productListMapper.insert(productList);

                        actionRecordService.createdAction(
                            "Create", 
                            "POST", 
                            "Product List", 
                            productList.getProductCode() + " " + productList.getProductName(), 
                            "Success"
                        );
                    } else {
                        productList.setId(checkProduct.getId());
                        productList.setUpdated(OffsetDateTime.now());
                        productListMapper.updateById(checkProduct);
                    }

                    List<ProductLocationUploadDto> productLocationUploads = productListUpload.getProductLocations();

                    if (productLocationUploads.size() > 0) {
                        for (ProductLocationUploadDto productLocationUpload : productLocationUploads) {
                            LambdaQueryWrapper<Location> queryWrapperLocation = Wrappers.lambdaQuery();

                            if(StringUtils.isNotBlank(productLocationUpload.getPlaceCode()) || StringUtils.isNotBlank(productLocationUpload.getPlaceName())) {
                                if(StringUtils.isNotBlank(productLocationUpload.getPlaceCode())) {
                                    queryWrapperLocation.eq(Location::getPlaceCode, productLocationUpload.getPlaceCode());
                                }
                                if(StringUtils.isNotBlank(productLocationUpload.getPlaceName())) {
                                    queryWrapperLocation.eq(Location::getPlaceName, productLocationUpload.getPlaceName());
                                }
                            } else {
                                throw new RuntimeException("No location name or code!");
                            }
                            
                            Location location = locationService.getOne(queryWrapperLocation);


                            LambdaQueryWrapper<ProductLocation> queryWrapperProductLocation = Wrappers.lambdaQuery();

                            queryWrapperProductLocation.eq(ProductLocation::getProductId, productList.getId());
                            queryWrapperProductLocation.eq(ProductLocation::getLocationId, location.getId());

                            ProductLocation productLocationCheck = productLocationService.getOne(queryWrapperProductLocation);

                            if (location == null) {
                                throw new RuntimeException("No this location in active data records!");
                            } else {

                                if (productLocationCheck == null) {
                                    productLocation.setLocationId(Math.toIntExact(location.getId()));
                                    productLocation.setProductId(Math.toIntExact(productList.getId()));
                                    productLocation.setQty(productLocationUpload.getQty());
                                    productLocation.setTotalPrice(productLocationUpload.getTotalPrice());

                                    productLocationMapper.insert(productLocation);
                                } else {
                                    productLocation.setId(productLocationCheck.getId());
                                    productLocation.setLocationId(Math.toIntExact(location.getId()));
                                    productLocation.setProductId(Math.toIntExact(productList.getId()));
                                    productLocation.setQty(productLocationCheck.getQty() +productLocationUpload.getQty());
                                    productLocation.setTotalPrice(productLocationCheck.getCost() + productLocationUpload.getTotalPrice());

                                    productLocationService.updateById(productLocation);

                                    
                                }
                                invRecord.setQty(productLocationUpload.getQty());
                                invRecord.setProductId(Math.toIntExact(productList.getId()));
                                invRecord.setLocFrom(0);
                                invRecord.setLocTo(Math.toIntExact(location.getId()));
                                invRecord.setCost(productLocationUpload.getTotalPrice());
                                invRecord.setTimeAt(OffsetDateTime.now());
                                
                                invRecordService.saveRecord(invRecord);
                            }
/* 
                            if(StringUtils.isNotBlank(productLocationUpload.getStockMovePlaceToCode()) || StringUtils.isNotBlank(productLocationUpload.getStockMovePlaceToName())) {
                                LambdaQueryWrapper<Location> queryWrapperLocationMove = Wrappers.lambdaQuery();

                            
                                if(StringUtils.isNotBlank(productLocationUpload.getStockMovePlaceToCode())) {
                                    queryWrapperLocationMove.eq(Location::getPlaceCode, productLocationUpload.getStockMovePlaceToCode());
                                }
                                if(StringUtils.isNotBlank(productLocationUpload.getStockMovePlaceToName())) {
                                    queryWrapperLocationMove.eq(Location::getPlaceName, productLocationUpload.getStockMovePlaceToName());
                                }
                             
                                Location locationMove = locationService.getOne(queryWrapperLocationMove);

                                LambdaQueryWrapper<ProductLocation> queryWrapperProductLocationMove = Wrappers.lambdaQuery();

                                queryWrapperProductLocationMove.eq(ProductLocation::getProductId, productList.getId());
                                queryWrapperProductLocationMove.eq(ProductLocation::getLocationId, location.getId());

                                ProductLocation productLocationMoveCheck = productLocationService.getOne(queryWrapperProductLocationMove);

                                if (locationMove == null) {
                                    throw new RuntimeException("No this location in active data records!");
                                } else {
                                    if (productLocationMoveCheck == null) {
                                        productLocation.setLocationId(Math.toIntExact(location.getId()));
                                        productLocation.setProductId(Math.toIntExact(productList.getId()));
                                        productLocation.setQty(productLocationUpload.getQty());
                                        productLocation.setTotalPrice(productLocationUpload.getTotalPrice());

                                        productLocationMapper.insert(productLocation);
                                    } else {
                                        productLocation.setId(productLocationCheck.getId());
                                        productLocation.setLocationId(Math.toIntExact(locationMove.getId()));
                                        productLocation.setProductId(Math.toIntExact(productList.getId()));
                                        productLocation.setQty(productLocationCheck.getQty() +productLocationUpload.getStockMovePlaceToQty());
                                        productLocation.setTotalPrice(productLocationCheck.getCost() + productLocationUpload.getStockMovePlaceToTotalPrice());
    
                                        productLocationService.updateById(productLocation);
                        
                                    }

                                    invRecord.setQty(productLocationUpload.getStockMovePlaceToQty());
                                    invRecord.setProductId(Math.toIntExact(productList.getId()));
                                    invRecord.setLocFrom(Math.toIntExact(location.getId()));
                                    invRecord.setLocTo(Math.toIntExact(locationMove.getId()));
                                    invRecord.setCost(productLocationUpload.getStockMovePlaceToTotalPrice());
                                    invRecord.setTimeAt(LocalDateTime.now());
                                    
                                    invRecordService.saveRecord(invRecord);
                                }
                            }
*/                    
                        }
                    }

                }


        }
    }

    public void createOne(ProductList productList) {
        LambdaQueryWrapper<ProductList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductList::getProductName, productList.getProductName());
        queryWrapper.eq(ProductList::getStatu, 1);
        ProductList checkOne = productListMapper.selectOne(queryWrapper);
        if (checkOne == null) {
            String newCode = getNewCode();
            productList.setProductCode(newCode);
            productList.setStatu(1);
            productList.setCreated(OffsetDateTime.now());
            productListMapper.insert(productList);

            List<ProductListFile> newProductListFiles = productList.getNewProductListFiles();

            if (newProductListFiles.size() > 0) {
                for (ProductListFile productListFile : newProductListFiles) {
                    productListFile.setProductId(Math.toIntExact(productList.getId()));
                    productListFileService.saveListPicture(productListFile);
                }
            }

            actionRecordService.createdAction(
                    "Create", 
                    "POST", 
                    "Product List", 
                    productList.getProductCode() + " " + productList.getProductName(), 
                    "Success"
            );

        } else {
            actionRecordService.createdAction(
                "Create", 
                "POST", 
                "Product List", 
                productList.getProductCode() + " " + productList.getProductName(), 
                "Failure"
            );
            throw new RuntimeException("Exist in records!");
        } 
    }

    public void voidOne(Long id) {
        LambdaQueryWrapper<ProductList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductList::getId, id);
        queryWrapper.eq(ProductList::getStatu, 1);
        ProductList checkOne = productListMapper.selectOne(queryWrapper);

        if (checkOne.getId().equals(id)) {
            productList.setId(id);
            productList.setStatu(0);
            productListMapper.updateById(productList);

            actionRecordService.createdAction(
                "Remove", 
                "DELETE", 
                "Product List", 
                productList.getProductCode() + " " + productList.getProductName(), 
                "Success"
            );
        } else {
            actionRecordService.createdAction(
                "Remove", 
                "DELETE", 
                "Product List", 
                productList.getProductCode() + " " + productList.getProductName(), 
                "Failure"
            );

            throw new RuntimeException("No active data in records!");
        }
    }

    public void updateOne(ProductList productList) {
        LambdaQueryWrapper<ProductList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductList::getProductCode, productList.getProductCode());
        queryWrapper.eq(ProductList::getStatu, 1);
        ProductList checkOne = productListMapper.selectOne(queryWrapper);

        if (checkOne.getProductCode().equals(productList.getProductCode())) {
            productList.setUpdated(OffsetDateTime.now());
            productListMapper.updateById(productList);

            List<ProductListFile> newProductListFiles = productList.getNewProductListFiles();

            if (newProductListFiles.size() > 0) {
                for (ProductListFile productListFile : newProductListFiles) {
                    productListFile.setProductId(Math.toIntExact(productList.getId()));
                    productListFileService.saveListPicture(productListFile);
                }
            } 

            actionRecordService.createdAction(
                "Update", 
                "POST", 
                "Product List", 
                productList.getProductCode() + " " + productList.getProductName(), 
                "Success"
            );
        } else {

            actionRecordService.createdAction(
                "Update", 
                "POST", 
                "Product List", 
                productList.getProductCode() + " " + productList.getProductName(), 
                "Failure"
            );

            throw new RuntimeException("No active data in records!");
        }
    }

    public ProductList findOne(ProductList productList) {
        LambdaQueryWrapper<ProductList> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(ProductList::getProductCode, productList.getProductCode());
        queryWrapper.eq(ProductList::getStatu, 1);
        ProductList one = productListMapper.selectOne(queryWrapper);
        return one;
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
