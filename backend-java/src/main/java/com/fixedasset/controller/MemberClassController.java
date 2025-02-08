package com.fixedasset.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.common.lang.Result;
import com.fixedasset.entity.MemberClass;
import com.fixedasset.service.MemberClassService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Tag(name = "Member Class")
@RestController
@RequestMapping("/base/member/class")
public class MemberClassController {

    @Resource private MemberClassService memberClassService;

    @Operation(summary = "Create")
    @PostMapping("/create")
    public Result create(@RequestBody MemberClass memberClass) {
        memberClassService.saveData(memberClass);
        return Result.succ(memberClass);
    }

    @Operation(summary = "Update")
    @PostMapping("/update")
    public Result update(@RequestBody MemberClass memberClass) {
        memberClassService.update(memberClass);
        return Result.succ(memberClass);
    }

    @Operation(summary = "Get one by id")
    @GetMapping("/{id}")
    public Result getOne(@PathVariable("id") Long id) {
        return Result.succ(memberClassService.getById(id));
    }

    @Operation(summary = "Void one by id")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable("id") Long id) {
        memberClassService.voidClass(id);
        return Result.succ(id);
    }

    @Operation(summary = "Get all list of member classes")
    @GetMapping("/getAll")
    public Result getAll() {
        return Result.succ(memberClassService.getAll());
    }

    @Operation(summary = "Page and list")
    @PostMapping("/listAll")
    public Result listAll(@RequestBody MemberClass memberClass) {
        Page page = new Page(memberClass.getPage(), memberClass.getLimit());
        LambdaQueryWrapper<MemberClass> queryWrapper = Wrappers.lambdaQuery();

        if (StringUtils.isNotBlank(memberClass.getSearch())) {
            queryWrapper.like(MemberClass::getName, memberClass.getSearch());
        }

        queryWrapper.eq(MemberClass::getStatus, 1);
        Page<MemberClass> iPage = memberClassService.page(page, queryWrapper);
        return Result.succ(iPage);
    }

}
