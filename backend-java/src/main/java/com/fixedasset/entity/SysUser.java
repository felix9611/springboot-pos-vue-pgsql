package com.fixedasset.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author WaiterXiaoYY
 * @since 2022-01-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUser extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @NotBlank(message = "Username Cannot Blank")
    private String username;

    private String password;

    private String avatar;

    @TableField("dept_id")
    private int deptId;

    @TableField("place_id")
    private int placeId;

    @TableField("avatar_base64")
    private String avatarBase64;

    @NotBlank(message = "Email Cannot Blank")
    @Email(message = "Email Format Incorrect")
    private String email;

    private String city;

    private OffsetDateTime lastLogin;

    @TableField(exist = false)
    private List<SysRole> sysRoles = new ArrayList<>();

    @TableField(exist = false)
    private int page;

    @TableField(exist = false)
    private int limit;

}
