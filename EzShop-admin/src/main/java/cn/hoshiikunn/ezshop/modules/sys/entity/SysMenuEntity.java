package cn.hoshiikunn.ezshop.modules.sys.entity;

import cn.hoshiikunn.ezshop.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_menu")
public class SysMenuEntity extends BaseEntity {

    /**
     * 父菜单ID
     */
    private Long pid;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单类型 M-目录 C-菜单 F-按钮
     */
    private String menuType;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 权限标识
     */
    private String perms;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 可见性 0-隐藏 1-显示
     */
    private Integer visible;

    /**
     * 打开方式 0-内部打开 1-外部打开
     */
    private Integer openStyle;

    /**
     * 状态 0-禁用 1-启用
     */
    private Integer status;
}