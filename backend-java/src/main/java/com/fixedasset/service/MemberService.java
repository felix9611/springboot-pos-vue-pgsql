package com.fixedasset.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fixedasset.entity.Member;
import com.fixedasset.entity.MemberSpecialDay;

import java.util.List;

public interface MemberService extends IService<Member> {
    void createNew(Member member);

    void voidMember(Long id);

    List<Member> getAll();

    void updateData(Member member);

    void saveSpecialDay(MemberSpecialDay memberSpecialDay);

    void updateSpecialDay(MemberSpecialDay memberSpecialDay);

    List<MemberSpecialDay> getSpecialDay(int memberId);

    void voidSpecialDay(Long id);

    Page<MemberSpecialDay> listSpecialDayPage(Page page, LambdaQueryWrapper<MemberSpecialDay> queryWrapper);

    MemberSpecialDay getOne(Long id);

    Member getOneMember(Long id);

    void importData(List<Member> members);
}
