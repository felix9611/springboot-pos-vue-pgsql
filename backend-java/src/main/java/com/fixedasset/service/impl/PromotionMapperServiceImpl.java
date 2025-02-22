package com.fixedasset.service.impl;

import java.time.OffsetDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.entity.Promotion;
import com.fixedasset.entity.PromotionDepartment;
import com.fixedasset.entity.PromotionLocation;
import com.fixedasset.entity.PromotionType;
import com.fixedasset.mapper.PromotionDepartmentMapper;
import com.fixedasset.mapper.PromotionLocationMapper;
import com.fixedasset.mapper.PromotionMapper;
import com.fixedasset.mapper.PromotionTypeMapper;
import com.fixedasset.service.ActionRecordService;
import com.fixedasset.service.PromotionService;

@Service
public class PromotionMapperServiceImpl extends ServiceImpl<PromotionMapper, Promotion> implements PromotionService {
    
    @Resource private PromotionMapper promotionMapper;

    @Resource private Promotion promotion;

    @Resource private PromotionDepartmentMapper promotionDepartmentMapper;

    @Resource private PromotionDepartment promotionDepartment;

    @Resource private PromotionLocationMapper promotionLocationMapper;

    @Resource private PromotionLocation promotionLocation;

    @Resource private PromotionTypeMapper promotionTypeMapper;

    @Resource private PromotionType promotionType;

    @Resource private ActionRecordService actionRecordService;

    public Promotion getById(Long id) {
        LambdaQueryWrapper<Promotion> lambdaQueryWrapper = Wrappers.lambdaQuery();

        lambdaQueryWrapper.eq(Promotion::getId, id);
        lambdaQueryWrapper.eq(Promotion::getStatu, 1);

        Promotion data = promotionMapper.selectOne(lambdaQueryWrapper);

        LambdaQueryWrapper<PromotionLocation> lambdaQueryWrapperLocation = Wrappers.lambdaQuery();

        lambdaQueryWrapperLocation.eq(PromotionLocation::getPromotionId, id);
        lambdaQueryWrapperLocation.eq(PromotionLocation::getStatu, 1);

        List<PromotionLocation> promotionLocations = promotionLocationMapper.selectList(lambdaQueryWrapperLocation);

        LambdaQueryWrapper<PromotionDepartment> lambdaQueryWrapperDepartment = Wrappers.lambdaQuery();

        lambdaQueryWrapperDepartment.eq(PromotionDepartment::getPromotionId, id);
        lambdaQueryWrapperDepartment.eq(PromotionDepartment::getStatu, 1);

        List<PromotionDepartment> promotionDepartments = promotionDepartmentMapper.selectList(lambdaQueryWrapperDepartment);

        LambdaQueryWrapper<PromotionType> lambdaQueryWrapperType = Wrappers.lambdaQuery();

        lambdaQueryWrapperType.eq(PromotionType::getPromotionId, id);
        lambdaQueryWrapperType.eq(PromotionType::getStatu, 1);

        List<PromotionType> promotionTypes = promotionTypeMapper.selectList(lambdaQueryWrapperType);
        
        data.setPromotionTypeItems(promotionTypes);
        data.setPromotionDepartmentItems(promotionDepartments);
        data.setPromotionLocationItems(promotionLocations);

        return data;
    }

    public void voidData(Long id) {
        Promotion checkOne = getById(id);

        if (checkOne != null) {
            promotion.setId(id);
            promotion.setStatu(0);
            promotion.setUpdated(OffsetDateTime.now());

            promotionMapper.updateById(promotion);
            
            actionRecordService.createdAction(
                "Remove", 
                "DELETE", 
                "Promotion Manager", 
                promotion.toString(), 
                "Success"
            );
            
        } else {

            actionRecordService.createdAction(
                "Remove", 
                "DELETE", 
               "Promotion Manager",
                id.toString(), 
                "Failure"
            );
        }

    }

    public void update(Promotion data) {
        Promotion checkOne = getById(data.getId());

        if (checkOne != null) {
            data.setUpdated(OffsetDateTime.now());
            promotionMapper.updateById(data);

            List<PromotionLocation> promotionLocations = data.getPromotionLocationItems();
            List<PromotionDepartment> promotionDepartments = data.getPromotionDepartmentItems();
            List<PromotionType> promotionTypes = data.getPromotionTypeItems();

            if (promotionLocations.size() > 0) {
                for (PromotionLocation promotionLocation : promotionLocations) {
                    if (promotionLocation.getId() != null) {
                        promotionLocation.setUpdated(OffsetDateTime.now());
                        promotionLocationMapper.updateById(promotionLocation);

                    } else {
                        promotionLocation.setCreated(OffsetDateTime.now());
                        promotionLocation.setStatu(1);
                        promotionLocation.setPromotionId(Math.toIntExact(data.getId()));

                        promotionLocationMapper.insert(promotionLocation);
                        
                    }
                }
            }

            if (promotionDepartments.size() > 0) {

                for (PromotionDepartment promotionDepartment : promotionDepartments) {
                    if (promotionDepartment.getId() != null) {
                        promotionDepartment.setUpdated(OffsetDateTime.now());
                        promotionDepartmentMapper.updateById(promotionDepartment);

                    } else {
                        promotionDepartment.setCreated(OffsetDateTime.now());
                        promotionDepartment.setStatu(1);
                        promotionDepartment.setPromotionId(Math.toIntExact(data.getId()));
                        promotionDepartmentMapper.insert(promotionDepartment);
                    }
                }
            }

            if (promotionTypes.size() > 0) {
            
                for (PromotionType promotionType : promotionTypes) {
                    if (promotionType.getId() != null) {
                        promotionType.setUpdated(OffsetDateTime.now());
                        promotionTypeMapper.updateById(promotionType);
                    } else {
                        promotionType.setCreated(OffsetDateTime.now());
                        promotionType.setStatu(1);
                        promotionType.setPromotionId(Math.toIntExact(data.getId()));

                        promotionTypeMapper.insert(promotionType);

                        
                    }
                }
            }

            actionRecordService.createdAction(
                "Update", 
                    "POST", 
                    "Promotion Manager", 
                    promotion.toString(), 
                    "Success"
            );

        }
    }

    public void create(Promotion newData) {
        LambdaQueryWrapper<Promotion> lambdaQueryWrapper = Wrappers.lambdaQuery();

        lambdaQueryWrapper.eq(Promotion::getPromotionCode, newData.getPromotionCode());
        lambdaQueryWrapper.eq(Promotion::getPromotionName, newData.getPromotionName());

        Promotion data = promotionMapper.selectOne(lambdaQueryWrapper);

        if (data == null) {

            newData.setCreated(OffsetDateTime.now());
            newData.setStatu(1);
            promotionMapper.insert(newData);

            List<PromotionLocation> promotionLocations = newData.getNewPromotionLocationItems();
            List<PromotionDepartment> promotionDepartments = newData.getNewPromotionDepartmentItems();
            List<PromotionType> promotionTypes = newData.getNewPromotionTypeItems();

            if (promotionLocations.size() > 0) {
                for (PromotionLocation promotionLocation : promotionLocations) {
                    promotionLocation.setCreated(OffsetDateTime.now());
                    promotionLocation.setStatu(1);
                    promotionLocation.setPromotionId(Math.toIntExact(newData.getId()));

                    promotionLocationMapper.insert(promotionLocation);
                }
            }

            if (promotionDepartments.size() > 0) {

                for (PromotionDepartment promotionDepartment : promotionDepartments) {
                    promotionDepartment.setCreated(OffsetDateTime.now());
                    promotionDepartment.setStatu(1);
                    promotionDepartment.setPromotionId(Math.toIntExact(newData.getId()));

                    promotionDepartmentMapper.insert(promotionDepartment);
                }
            }

            if (promotionTypes.size() > 0) {
            
                for (PromotionType promotionType : promotionTypes) {
                    promotionType.setCreated(OffsetDateTime.now());
                    promotionType.setStatu(1);
                    promotionType.setPromotionId(Math.toIntExact(newData.getId()));

                    promotionTypeMapper.insert(promotionType);
                }
            }

            actionRecordService.createdAction(
                "Save", 
                    "POST", 
                    "Promotion Manager", 
                    promotion.toString(), 
                    "Success"
            );


        } else {
            actionRecordService.createdAction(
                "Save", 
                "POST", 
                "Promotion Manager", 
                promotion.toString(),
                "Failure"
            );

            throw new RuntimeException("Exist in records! Please check again!");
        }
    }

    public void removePromotionType(Long id) {
        LambdaQueryWrapper<PromotionType> lambdaQueryWrapper = Wrappers.lambdaQuery();

        lambdaQueryWrapper.eq(PromotionType::getId, id);
        lambdaQueryWrapper.eq(PromotionType::getStatu, 1);

        PromotionType data = promotionTypeMapper.selectOne(lambdaQueryWrapper);

        if (data.getId() != null) {
            PromotionType promotionType = new PromotionType();

            promotionType.setId(id);
            promotionType.setStatu(0);
            promotionType.setUpdated(OffsetDateTime.now());

            promotionTypeMapper.updateById(promotionType);

            actionRecordService.createdAction(
                    "Remove", 
                    "DELETE", 
                    "Promotion Manager for Promotion Type",
                    promotionType.toString(), 
                    "Success"
            );


        } else {
            actionRecordService.createdAction(
                "Remove", 
                "DELETE", 
                "Promotion Manager for Promotion Type", 
                id.toString(),
                "Failure"
            );

            throw new RuntimeException("No in records! Please check again!");
        }

        
    }

    public void removePromotionDepartment(Long id) {
        LambdaQueryWrapper<PromotionDepartment> lambdaQueryWrapper = Wrappers.lambdaQuery();

        lambdaQueryWrapper.eq(PromotionDepartment::getId, id);
        lambdaQueryWrapper.eq(PromotionDepartment::getStatu, 1);

        PromotionDepartment data = promotionDepartmentMapper.selectOne(lambdaQueryWrapper);

        if (data.getId() != null) {
            PromotionDepartment promotionDepartment = new PromotionDepartment();

            promotionDepartment.setId(id);
            promotionDepartment.setStatu(0);
            promotionDepartment.setUpdated(OffsetDateTime.now());

            promotionDepartmentMapper.updateById(promotionDepartment);

            actionRecordService.createdAction(
                    "Remove", 
                    "DELETE", 
                    "Promotion Manager for Promotion Department",
                    promotionDepartment.toString(), 
                    "Success"
            );


        } else {
            actionRecordService.createdAction(
                "Remove", 
                "DELETE", 
                "Promotion Manager for Promotion Department", 
                id.toString(),
                "Failure"
            );

            throw new RuntimeException("No in records! Please check again!");
        }
        
    }

    public void removePromotionLocation(Long id) {
        LambdaQueryWrapper<PromotionLocation> lambdaQueryWrapper = Wrappers.lambdaQuery();

        lambdaQueryWrapper.eq(PromotionLocation::getId, id);
        lambdaQueryWrapper.eq(PromotionLocation::getStatu, 1);

        PromotionLocation data = promotionLocationMapper.selectOne(lambdaQueryWrapper);

        if (data.getId() != null) {
            PromotionLocation promotionLocation = new PromotionLocation();

            promotionLocation.setId(id);
            promotionLocation.setStatu(0);
            promotionLocation.setUpdated(OffsetDateTime.now());

            promotionLocationMapper.updateById(promotionLocation);

            actionRecordService.createdAction(
                    "Remove", 
                    "DELETE", 
                    "Promotion Manager for Promotion Location",
                    promotionLocation.toString(), 
                    "Success"
            );


        } else {
            actionRecordService.createdAction(
                "Remove", 
                "DELETE", 
                "Promotion Manager for Promotion Location", 
                id.toString(),
                "Failure"
            );

            throw new RuntimeException("No in records! Please check again!");
        }
        
    }


    public List<Promotion> todayPromotion() {
        List<Promotion> promotions = promotionMapper.listTodayPromotion();

        if (promotions.size() > 0) {
            for (Promotion promotion : promotions) {
                List<PromotionType> listPromotionTypeDetail = promotionTypeMapper.listPromotionTypeDetail(promotion.getId().intValue());
                promotion.setPromotionTypeItems(listPromotionTypeDetail);

                List<PromotionLocation> listPromotionLocationDetail = promotionLocationMapper.listPromotionLocationDetail(promotion.getId().intValue());
                promotion.setPromotionLocationItems(listPromotionLocationDetail);

                List<PromotionDepartment> listPromotionDepartmentDetail = promotionDepartmentMapper.listPromotionDepartmentDetail(promotion.getId().intValue());
                promotion.setPromotionDepartmentItems(listPromotionDepartmentDetail);
            }
            return promotions;
        }


        return promotions;
    }
}
