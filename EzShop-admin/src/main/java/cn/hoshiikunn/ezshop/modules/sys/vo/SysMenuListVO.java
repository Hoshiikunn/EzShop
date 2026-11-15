package cn.hoshiikunn.ezshop.modules.sys.vo;

import cn.hoshiikunn.ezshop.modules.sys.entity.SysMenuEntity;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 菜单列表信息
 */
@Data
@Schema(description = "菜单列表信息")
public class SysMenuListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单ID")
    private Long id;

    @Schema(description = "父ID")
    private Long pid;

    @Schema(description = "菜单名称")
    private String menuName;

    @Schema(description = "菜单类型 M-目录 C-菜单 F-按钮")
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

    /**
     * 实体 转 VO
     */
    public static SysMenuListVO of(SysMenuEntity entity) {
        return BeanUtil.toBean(entity, SysMenuListVO.class);
    }
}
