package cn.hoshiikunn.ezshop.modules.sys.dto;

import cn.hoshiikunn.ezshop.validation.group.AddGroup;
import cn.hoshiikunn.ezshop.validation.group.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 菜单参数
 */
@Data
@Schema(description = "菜单参数")
public class SysMenuDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单ID")
    @Null(message = "菜单ID必须为空", groups = AddGroup.class)
    @NotNull(message = "菜单ID不能为空", groups = UpdateGroup.class)
    private Long id;

    @Schema(description = "父菜单ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "父菜单为必填项")
    private Long pid;

    @Schema(description = "菜单名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "菜单名称为必填项")
    @Length(max = 50, message = "菜单名称长度不可超过 50 字符")
    private String menuName;

    @Schema(description = "菜单类型 M-目录 C-菜单 F-权限", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "菜单类型为必填项")
    @Pattern(regexp = "^[MCF]$", message = "菜单类型只能是 M / C / F")
    private String menuType;

    @Schema(description = "路由地址")
    @Length(max = 255, message = "路由地址长度不可超过 255 字符")
    private String path;

    @Schema(description = "组件路径")
    @Length(max = 255, message = "组件路径长度不可超过 255 字符")
    private String component;

    @Schema(description = "权限标识")
    @Length(max = 255, message = "权限标识长度不可超过 255 字符")
    @Pattern(regexp = "^$|^[a-zA-Z][a-zA-Z0-9:_-]*$", message = "权限标识格式错误")
    private String perms;

    @Schema(description = "菜单图标")
    @Length(max = 50, message = "菜单图标长度不可超过 50 字符")
    private String icon;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序为必填项")
    @Range(min = 0, max = 9999, message = "排序取值范围为 0-9999")
    private Integer sort;

    @Schema(description = "可见性 0-隐藏 1-显示", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "可见性为必填项")
    @Range(min = 0, max = 1, message = "可见性取值只能为 0(隐藏) 1(显示)")
    private Integer visible;

    @Schema(description = "打开方式 0-内部 1-外部")
    @Range(min = 0, max = 1, message = "打开方式取值只能为 0(内部) 1(外部)")
    private Integer openStyle;
}
