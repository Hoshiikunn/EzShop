package cn.hoshiikunn.ezshop.modules.sys.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

/**
 * 系统用户信息
 */
@Data
@Schema(description = "系统用户信息")
public class SysUserInfoVO {

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "头像 Url")
    private String avatarUrl;

    @Schema(description = "性别 0-女 1-男")
    private Integer gender;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "状态 0-禁用 1-启用")
    private Integer status;

    @Schema(description = "是否超级管理员 0-否 1-是")
    private Integer isSuperAdmin;

    @Schema(description = "最后登录IP地址")
    private String lastLoginIp;

    @Schema(description = "最后登录时间")
    private Date lastLoginTime;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;
}
