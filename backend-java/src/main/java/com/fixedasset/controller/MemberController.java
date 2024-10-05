package com.fixedasset.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.common.lang.Result;
import com.fixedasset.entity.MemberSpecialDay;
import com.fixedasset.service.MemberClassService;
import com.fixedasset.service.MemberSpecialDaysService;
import org.apache.ibatis.annotations.Delete;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fixedasset.entity.Member;
import com.fixedasset.service.MemberService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/base/member")
public class MemberController extends BaseController {

    @Resource private MemberService memberService;

    @Resource private MemberSpecialDaysService memberSpecialDaysService;

    @PostMapping("/create")
    public Result save(@RequestBody Member member) {
        memberService.createNew(member);
        return Result.succ(member);
    }

    @PostMapping("/update")
    // @PreAuthorize("hasAuthority('base:dept:update')")
    public Result update(@RequestBody Member member) {
        memberService.updateData(member);
        return Result.succ(member);
    }

    @GetMapping("/{id}")
    public Result getOne(@PathVariable("id") Long id) {
        return Result.succ(memberService.getById(id));
    }

    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable("id") Long id) {
        memberService.voidMember(id);
        return Result.succ(id);
    }

    @PostMapping("/listAll")
    public Result listAll(@RequestBody Member member) {
        Page page = new Page(member.getPage(), member.getLimit());
        LambdaQueryWrapper<Member> queryWrapper = Wrappers.lambdaQuery();

        if (StringUtils.isNotBlank(member.getPhone())) {
            queryWrapper.like(Member::getPhone, member.getPhone());
        }

        queryWrapper.eq(Member::getStatus, 1);
        Page<Member> iPage = memberService.page(page, queryWrapper);
        return Result.succ(iPage);
    }

    @GetMapping("/getAll")
    public Result getAll() {
        return Result.succ(memberService.getAll());
    }

    // @GetMapping("/specialDay/{memberId}")
    // public Result getAllSpecialDay(@PathVariable("id") int memberId) {
    //     return Result.succ(memberService.getSpecialDay(memberId));
    // }

    @PostMapping("/specialDay/list")
    public Result listAllSpecialDay(@RequestBody MemberSpecialDay memberSpecialDay) {
        Page page = new Page(memberSpecialDay.getPage(), memberSpecialDay.getLimit());
        LambdaQueryWrapper<MemberSpecialDay> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(MemberSpecialDay::getMemberId, memberSpecialDay.getMemberId());

        Page<MemberSpecialDay> iPage = memberService.listSpecialDayPage(page, queryWrapper);
        return Result.succ(iPage);
    }

    @PostMapping("/specialDay/save")
    public Result saveSpecialDay(@RequestBody MemberSpecialDay memberSpecialDay) {
        memberService.saveSpecialDay(memberSpecialDay);
        return Result.succ(memberSpecialDay);
    }

    @PostMapping("/specialDay/update")
    public Result updateSpecialDay(@RequestBody MemberSpecialDay memberSpecialDay) {
        memberService.updateSpecialDay(memberSpecialDay);
        return Result.succ(memberSpecialDay);
    }

    @PostMapping("/specialDay/void/{id}")
    public Result updateSpecialDay(@PathVariable("id")Long id) {
        // memberService.voidSpecialDay(id);
        return Result.succ(id);
    }

    @GetMapping("/specialDay/{id}")
    public Result getOneSpecialDay(@PathVariable("id")Long id) {
        return Result.succ(memberSpecialDaysService.getById(id));
    }

    @PostMapping("/find")
    public Result findMember(@RequestBody Member member) {
        LambdaQueryWrapper<Member> queryWrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(member.getName())) {
            queryWrapper.like(Member::getName, member.getName());
        }

        if (StringUtils.isNotBlank(member.getPhone())) {
            queryWrapper.like(Member::getPhone, member.getPhone());
        }

        queryWrapper.eq(Member::getStatus, 1);
        return Result.succ(memberService.list(queryWrapper));
    }


}
