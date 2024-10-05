package com.fixedasset.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fixedasset.common.dto.PassDto;
import com.fixedasset.common.lang.Const;
import com.fixedasset.common.lang.Result;
import com.fixedasset.dto.ResetPasswordDto;
import com.fixedasset.entity.LoginRecord;
import com.fixedasset.entity.SysRole;
import com.fixedasset.entity.SysUser;
import com.fixedasset.entity.SysUserRole;
import com.fixedasset.service.LoginRecordService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author WaiterXiaoYY
 * @since 2022-01-13
 */
@RestController
@RequestMapping("/sys/user")
public class SysUserController extends BaseController {

    @Resource BCryptPasswordEncoder passwordEncoder;

    @Resource private LoginRecordService loginRecordService;

    @GetMapping("/info/{id}")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Result info(@PathVariable("id") Long id) {

        SysUser sysUser = sysUserService.getById(id);
        Assert.notNull(sysUser, "Not found the admin");

        List<SysRole> roles = sysRoleService.listRolesByUserId(id);

        sysUser.setSysRoles(roles);
        return Result.succ(sysUser);
    }

    @PostMapping("/list")
    @PreAuthorize("hasAuthority('sys:user:list')")
    public Result list(@RequestBody SysUser sysUser) {
        Page page = new Page(sysUser.getPage(), sysUser.getLimit());
        LambdaQueryWrapper<SysUser> queryWrapper = Wrappers.lambdaQuery();

        if(!StringUtils.isBlank(sysUser.getUsername())) {
            queryWrapper.like(SysUser::getUsername, sysUser.getUsername());
        }
        Page<SysUser> iPage = sysUserService.page(page, queryWrapper);
        return Result.succ(iPage);
        /*
        Page<SysUser> pageData = sysUserService.page(getPage(), new QueryWrapper<SysUser>()
                .like(StrUtil.isNotBlank(sysUser.getUsername()), "username", sysUser.getUsername()));

        pageData.getRecords().forEach(u -> {

            u.setSysRoles(sysRoleService.listRolesByUserId(u.getId()));
        });

        return Result.succ(pageData);
      */
    }

    @PostMapping("/save")
    @PreAuthorize("hasAuthority('sys:user:save')")
    public Result save(@Validated @RequestBody SysUser sysUser) {

        sysUser.setCreated(OffsetDateTime.now());
        sysUser.setStatu(Const.STATUS_ON);

        String password = passwordEncoder.encode(Const.DEFULT_PASSWORD);
        sysUser.setPassword(password);

        sysUser.setAvatar(Const.DEFULT_AVATAR);

        sysUserService.save(sysUser);
        return Result.succ(sysUser);
    }

    @PostMapping("/update")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result update(@Validated @RequestBody SysUser sysUser) {

        sysUser.setUpdated(OffsetDateTime.now());

        sysUserService.updateById(sysUser);
        return Result.succ(sysUser);
    }

    @Transactional
    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result delete(@RequestBody Long[] ids) {

        sysUserService.removeByIds(Arrays.asList(ids));
        sysUserRoleService.remove(new QueryWrapper<SysUserRole>().in("user_id", ids));

        return Result.succ("");
    }

    @Transactional
    @PostMapping("/role/{userId}")
    @PreAuthorize("hasAuthority('sys:user:role')")
    public Result rolePerm(@PathVariable("userId") Long userId, @RequestBody Long[] roleIds) {

        List<SysUserRole> userRoles = new ArrayList<>();

        Arrays.stream(roleIds).forEach(r -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setRoleId(r);
            sysUserRole.setUserId(userId);

            userRoles.add(sysUserRole);
        });

        sysUserRoleService.remove(new QueryWrapper<SysUserRole>().eq("user_id", userId));
        sysUserRoleService.saveBatch(userRoles);

        // Delete cache
        SysUser sysUser = sysUserService.getById(userId);
        sysUserService.clearUserAuthorityInfo(sysUser.getUsername());

        return Result.succ("");
    }

    @PostMapping("/self/repass")
    public Result repass(@RequestBody ResetPasswordDto resetPasswordDto) {

        SysUser sysUser = sysUserService.getByUsername(resetPasswordDto.getUsername());
        sysUser.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
        sysUserService.updateById(sysUser);

        return Result.succ("");
    }

    @PostMapping("/repass")
    @PreAuthorize("hasAuthority('sys:user:repass')")
    public Result repass(@RequestBody Long userId) {

        SysUser sysUser = sysUserService.getById(userId);

        sysUser.setPassword(passwordEncoder.encode(Const.DEFULT_PASSWORD));
        sysUser.setUpdated(OffsetDateTime.now());

        sysUserService.updateById(sysUser);
        return Result.succ("");
    }

    @PutMapping("/updateIcon")
    public Result repass(@RequestBody SysUser sysUser) {
        sysUser.setUpdated(OffsetDateTime.now());
        sysUserService.updateById(sysUser);
        return Result.succ(sysUser);
    }

    @PostMapping("/updatePass")
    public Result updatePass(@Validated @RequestBody PassDto passDto, Principal principal) {

        SysUser sysUser = sysUserService.getByUsername(principal.getName());

        boolean matches = passwordEncoder.matches(passDto.getCurrentPass(), sysUser.getPassword());
        if (!matches) {
            return Result.fail("旧密码不正确");
        }

        sysUser.setPassword(passwordEncoder.encode(passDto.getPassword()));
        sysUser.setUpdated(OffsetDateTime.now());

        sysUserService.updateById(sysUser);
        return Result.succ("");
    }

    @PostMapping("/saveRecord")
    public Result saveRecord(@RequestBody LoginRecord loginRecord) {
        loginRecordService.saveData(loginRecord);
        return Result.succ(loginRecord);
    }

    @PostMapping("/listLoginRecord/{username}")
    public Result listRecord(@PathVariable("username") String username) {
        Page<LoginRecord> page = new Page(1, 10);
        LambdaQueryWrapper<LoginRecord> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(LoginRecord::getUsername,  username);
        queryWrapper.orderBy(true, false, LoginRecord::getLoginTime);
        Page<LoginRecord> iPage = loginRecordService.page(page, queryWrapper);
        return Result.succ(iPage);
    }
}
