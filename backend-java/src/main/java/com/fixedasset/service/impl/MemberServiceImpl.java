package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.entity.Member;
import com.fixedasset.entity.MemberSpecialDay;
import com.fixedasset.mapper.MemberMapper;
import com.fixedasset.mapper.MemberSpecialDayMapper;
import com.fixedasset.service.MemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Resource private MemberMapper memberMapper;

    @Resource private MemberSpecialDayMapper memberSpecialDayMapper;

    @Resource private Member member;

    @Resource private MemberSpecialDay memberSpecialDay;

    public void createNew(Member member) {
        member.setCreatedAt(OffsetDateTime.now());
        member.setStatus(1);
        memberMapper.insert(member);
    }

    public void voidMember(Long id) {
        member.setId(id);
        member.setStatus(0);
        memberMapper.updateById(member);
    }

    public  void updateData(Member member) {
        member.setUpdatedAt(OffsetDateTime.now());
        memberMapper.updateById(member);
    }

    public List<Member> getAll() { return memberMapper.getAll(); }

    public List<MemberSpecialDay> getSpecialDay(int memberId) {
        return memberSpecialDayMapper.getSpecialDay(memberId);
    }

    public void saveSpecialDay(MemberSpecialDay memberSpecialDay) {
        memberSpecialDay.setStatus(1);
        memberSpecialDay.setCreatedAt(OffsetDateTime.now());
        memberSpecialDayMapper.insert(memberSpecialDay);
    }

    public void updateSpecialDay(MemberSpecialDay memberSpecialDay) {
        memberSpecialDay.setUpdatedAt(OffsetDateTime.now());
        memberSpecialDayMapper.updateById(memberSpecialDay);
    }

    public void voidSpecialDay(Long id) {
        memberSpecialDay.setId(id);
        memberSpecialDay.setStatus(0);
        memberSpecialDayMapper.updateById(memberSpecialDay);
    }

    public MemberSpecialDay getOne(Long id) {
        return memberSpecialDayMapper.selectById(id);
    }

    public Page<MemberSpecialDay> listSpecialDayPage(Page page, LambdaQueryWrapper<MemberSpecialDay> queryWrapper) {
        return memberSpecialDayMapper.selectPage(page, queryWrapper);
    }
}
