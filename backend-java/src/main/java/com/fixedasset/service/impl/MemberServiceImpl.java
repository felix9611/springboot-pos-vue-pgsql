package com.fixedasset.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fixedasset.entity.Member;
import com.fixedasset.entity.MemberClass;
import com.fixedasset.entity.MemberSpecialDay;
import com.fixedasset.mapper.MemberClassMapper;
import com.fixedasset.mapper.MemberMapper;
import com.fixedasset.mapper.MemberSpecialDayMapper;
import com.fixedasset.service.ActionRecordService;
import com.fixedasset.service.MemberService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.OffsetDateTime;
import java.util.List;

@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Resource private MemberMapper memberMapper;

    @Resource private MemberSpecialDayMapper memberSpecialDayMapper;

    @Resource private Member member;

    @Resource private MemberSpecialDay memberSpecialDay;

    @Resource private MemberClassMapper memberClassMapper;

    @Resource private ActionRecordService actionRecordService;

    public void importData(List<Member> members) {
        for (Member member : members) {

            LambdaQueryWrapper<MemberClass> queryWrapper = Wrappers.lambdaQuery();
            queryWrapper.eq(MemberClass::getName, member.getAddress());

            MemberClass checkOne = memberClassMapper.selectOne(queryWrapper);

            if (checkOne != null) {
                member.setClasses(Math.toIntExact(checkOne.getId()));
            }

            createNew(member);
        }
    }

    public void createNew(Member member) {
        LambdaQueryWrapper<Member> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Member::getName, member.getName());
        queryWrapper.eq(Member::getStatus, 1);
        Member checkOne = memberMapper.selectOne(queryWrapper);

        if (checkOne == null) {
            member.setCreatedAt(OffsetDateTime.now());
            member.setStatus(1);
            memberMapper.insert(member);

            List<MemberSpecialDay> memberSpecialDays = member.getMemberSpecialDays();

            if (memberSpecialDays.size() > 0) {
                for (MemberSpecialDay memberSpecialDay : memberSpecialDays) {
                  //  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");
                  //  LocalDateTime date = LocalDateTime.parse(memberSpecialDay.getDate(), formatter);

                 //   String newData = date.format(formatter);
           //         memberSpecialDay.setDate(newData);
                    memberSpecialDay.setMemberId(Math.toIntExact(member.getId()));
                    memberSpecialDay.setStatus(1);
                    memberSpecialDay.setCreatedAt(OffsetDateTime.now());
                    memberSpecialDayMapper.insert(memberSpecialDay);
                }
            }
        } else {
            throw new RuntimeException("Exist this member!");
        }
    }

    public void voidMember(Long id) {
        LambdaQueryWrapper<Member> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Member::getId, id);
        queryWrapper.eq(Member::getStatus, 1);
        Member checkOne = memberMapper.selectOne(queryWrapper);
        if (checkOne.getId().equals(member.getId())) {
            member.setId(id);
            member.setStatus(0);
            memberMapper.updateById(member);
        } else {
            throw new RuntimeException("No active data in records!");
        }
    }

    public void updateData(Member member) {
        LambdaQueryWrapper<Member> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Member::getId, member.getId());
        queryWrapper.eq(Member::getStatus, 1);
        Member checkOne = memberMapper.selectOne(queryWrapper);
        if (checkOne.getId().equals(member.getId())) {
            
            member.setUpdatedAt(OffsetDateTime.now());
            memberMapper.updateById(member);

            List<MemberSpecialDay> memberSpecialDays = member.getMemberSpecialDays();
            if (memberSpecialDays.size() > 0) {
                for (MemberSpecialDay memberSpecialDay : memberSpecialDays) {

                    if(memberSpecialDay.getId() == null) {
                        memberSpecialDay.setMemberId(Math.toIntExact(member.getId()));
                        memberSpecialDay.setStatus(1);
                        memberSpecialDay.setCreatedAt(OffsetDateTime.now());
                        memberSpecialDayMapper.insert(memberSpecialDay);
                    } else {
                        memberSpecialDay.setUpdatedAt(OffsetDateTime.now());
                        memberSpecialDayMapper.updateById(memberSpecialDay);
                    }
                }
            }
        } else {
            throw new RuntimeException("No active data in records!");
        }
    }

    public List<Member> getAll() { return memberMapper.getAll(); }

    public List<MemberSpecialDay> getSpecialDay(int memberId) {
        LambdaQueryWrapper<MemberSpecialDay> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(MemberSpecialDay::getMemberId, memberId);
        queryWrapper.eq(MemberSpecialDay::getStatus, 1);
        return memberSpecialDayMapper.selectList(queryWrapper);
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

    public Member getOneMember(Long id) {
        LambdaQueryWrapper<Member> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Member::getId, id);
        queryWrapper.eq(Member::getStatus, 1);

        Member member = memberMapper.selectOne(queryWrapper);

        LambdaQueryWrapper<MemberSpecialDay> queryWrapperMemberSpecialDay = Wrappers.lambdaQuery();
        queryWrapperMemberSpecialDay.eq(MemberSpecialDay::getMemberId, Math.toIntExact(1));
        queryWrapperMemberSpecialDay.eq(MemberSpecialDay::getStatus, 1);

        List<MemberSpecialDay> memberSpecialDays = memberSpecialDayMapper.selectList(queryWrapperMemberSpecialDay);
        member.setMemberSpecialDays(memberSpecialDays);

        return member;
    }

    public MemberSpecialDay getOne(Long id) {
        return memberSpecialDayMapper.selectById(id);
    }

    public Page<MemberSpecialDay> listSpecialDayPage(Page page, LambdaQueryWrapper<MemberSpecialDay> queryWrapper) {
        return memberSpecialDayMapper.selectPage(page, queryWrapper);
    }


}
