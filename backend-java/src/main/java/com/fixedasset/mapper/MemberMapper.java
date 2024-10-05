package com.fixedasset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixedasset.entity.Member;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MemberMapper extends BaseMapper<Member> {

    @Select("Select * from member where status =1")
    List<Member> getAll();
}
