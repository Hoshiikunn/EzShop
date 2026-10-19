package cn.hoshiikunn.ezshop.modules.sys.entity;

import cn.hoshiikunn.ezshop.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 系统用户实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user")
public class SysUserEntity extends BaseEntity {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 头像 Url
     */
    private String avatarUrl;

    /**
     * 性别 0-女 1-男
     */
    private Integer gender;

    /**
     * 电话号码
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态 0-禁用 1-启用
     */
    private Integer status;

    /**
     * 是否超级管理员 0-否 1-是
     */
    private Integer isSuperAdmin;

    /**
     * 最后登录IP地址
     */
    private String lastLoginIp;

    /**
     * 最后登录时间
     */
    private Date lastLoginTime;

    /**
     * 删除标记 0-未删除 1-已删除
     */
    @TableLogic
    private Integer isDeleted;

    /**
     * 删除时间
     */
    private Date deleteTime;

    /**
     * 版本号
     */
    @Version
    private Integer version;
}
