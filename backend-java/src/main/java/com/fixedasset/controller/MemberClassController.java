package com.fixedasset.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.common.lang.Result;
import com.fixedasset.entity.MemberClass;
import com.fixedasset.service.MemberClassService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/base/member/class")
public class MemberClassController {

    @Resource private MemberClassService memberClassService;

    @PostMapping("/create")
    public Result create(@RequestBody MemberClass memberClass) {
        memberClassService.saveData(memberClass);
        return Result.succ(memberClass);
    }

    @PostMapping("/update")
    public Result update(@RequestBody MemberClass memberClass) {
        memberClassService.update(memberClass);
        return Result.succ(memberClass);
    }

    @GetMapping("/{id}")
    public Result getOne(@PathVariable("id") Long id) {
        return Result.succ(memberClassService.getById(id));
    }

    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable("id") Long id) {
        memberClassService.voidClass(id);
        return Result.succ(id);
    }

    @GetMapping("/getAll")
    public Result getAll() {
        return Result.succ(memberClassService.getAll());
    }

    @PostMapping("/listAll")
    public Result listAll(@RequestBody MemberClass memberClass) {
        Page page = new Page(memberClass.getPage(), memberClass.getLimit());
        LambdaQueryWrapper<MemberClass> queryWrapper = Wrappers.lambdaQuery();

        queryWrapper.eq(MemberClass::getStatus, 1);
        Page<MemberClass> iPage = memberClassService.page(page, queryWrapper);
        return Result.succ(iPage);
    }

}
