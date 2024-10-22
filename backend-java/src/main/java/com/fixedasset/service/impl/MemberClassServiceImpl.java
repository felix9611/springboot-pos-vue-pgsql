package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.entity.ActionRecord;
import com.fixedasset.entity.MemberClass;
import com.fixedasset.mapper.ActionRecordMapper;
import com.fixedasset.mapper.MemberClassMapper;
import com.fixedasset.mapper.MemberMapper;
import com.fixedasset.service.ActionRecordService;
import com.fixedasset.service.MemberClassService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class MemberClassServiceImpl extends ServiceImpl<MemberClassMapper, MemberClass> implements MemberClassService {

    @Resource private MemberClassMapper memberClassMapper;

    @Resource private MemberClass memberClass;

    @Resource private ActionRecordService actionRecordService;

    public List<MemberClass> getAll() {
        LambdaQueryWrapper<MemberClass> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MemberClass::getStatus, 1);
        return memberClassMapper.selectList(queryWrapper);
    }

    public void saveData(MemberClass memberClass){
        LambdaQueryWrapper<MemberClass> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MemberClass::getName, memberClass.getName());
        queryWrapper.eq(MemberClass::getStatus, 1);

        MemberClass checkOne = memberClassMapper.selectOne(queryWrapper);

        if (checkOne == null)  {
            memberClass.setCreatedAt(OffsetDateTime.now());
            memberClass.setStatus(1);
            memberClassMapper.insert(memberClass);
            actionRecordService.createdAction(
                "Save", 
                "POST", 
                "Member Class Manger", 
                memberClass.toString(), 
                "Success"
            );
        } else {

            actionRecordService.createdAction(
                "Save", 
                "POST", 
                "Member Class Manger", 
                memberClass.toString(), 
                "Failure"
            );
            
            throw new RuntimeException("Exist in records! Please check again!");
        }
        
    }

    public void update(MemberClass memberClass){
        
        LambdaQueryWrapper<MemberClass> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MemberClass::getName, memberClass.getName());
        queryWrapper.eq(MemberClass::getId, mapperClass.getName());
        queryWrapper.eq(MemberClass::getStatus, 1);

        MemberClass checkOne = memberClassMapper.selectOne(queryWrapper);

        if (checkOne != null)  {
            memberClass.setUpdatedAt(OffsetDateTime.now());
            memberClassMapper.updateById(memberClass);
            actionRecordService.createdAction(
                "Update", 
                "POST", 
                "Member Class Manger", 
                memberClass.toString(), 
                "Success"
            );
        } else {
            actionRecordService.createdAction(
                "Update", 
                "POST", 
                "Member Class Manger", 
                memberClass.toString(), 
                "Failure"
            );
            throw new RuntimeException("No active data in records!");
        }
    }

    public void voidClass(Long id) {
        LambdaQueryWrapper<MemberClass> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MemberClass::getId, id);
        queryWrapper.eq(MemberClass::getStatus, 1);

        MemberClass checkOne = memberClassMapper.selectOne(queryWrapper);

        if (checkOne.getId().equals(id)) {
            memberClass.setId(id);
            memberClass.setStatus(0);
            memberClassMapper.updateById(memberClass);

            actionRecordService.createdAction(
                "Remove", 
                "DELETE", 
                "Member Class Manger", 
                memberClass.toString(), 
                "Success"
            );
        } else {
            actionRecordService.createdAction(
                "Remove", 
                "DELETE", 
                "Member Class Manger", 
                memberClass.toString(), 
                "Failure"
            );
            throw new RuntimeException("No active data in records!");
        }
    }

}
