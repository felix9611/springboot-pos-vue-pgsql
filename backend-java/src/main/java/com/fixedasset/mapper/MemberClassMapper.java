package com.fixedasset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixedasset.entity.MemberClass;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface MemberClassMapper extends BaseMapper<MemberClass> {
    @Select("Select * from member_class where status =1")
    List<MemberClass> getAll();
}
