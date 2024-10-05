package com.fixedasset.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fixedasset.entity.MemberSpecialDay;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MemberSpecialDayMapper extends BaseMapper<MemberSpecialDay> {

    @Select("Select * from member_special_day where member_id = #{memberId} and status = 1")
    List<MemberSpecialDay> getSpecialDay(@Param("memberId")int memberId);
}
