package cn.hoshiikunn.ezshop.modules.sys.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 菜单信息
 */
@Data
@Schema(description = "菜单信息")
public class SysMenuInfoVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单ID")
    private Long id;

    @Schema(description = "父ID")
    private Long pid;

    @Schema(description = "菜单名称")
    private String menuName;

    @Schema(description = "菜单类型 M-目录 C-菜单 F-权限")
    private String menuType;

    @Schema(description = "路由地址")
    private String path;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "权限标识")
    private String perms;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "可见性 0-隐藏 1-显示")
    private Integer visible;

    @Schema(description = "打开方式 0-内部 1-外部")
    private Integer openStyle;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;
}
