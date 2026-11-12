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
import java.util.Set;

/**
 * 角色参数
 */
@Data
@Schema(description = "角色参数")
public class SysRoleDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色ID")
    @Null(message = "角色ID必须为空", groups = AddGroup.class)
    @NotNull(message = "角色ID不能为空", groups = UpdateGroup.class)
    private Long id;

    @Schema(description = "角色名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "角色名称为必填项")
    @Length(max = 30, message = "角色名称长度不可超过 30 字符")
    private String roleName;

    @Schema(description = "角色权限字符")
    @NotBlank(message = "权限字符为必填项", groups = AddGroup.class)
    @Null(message = "权限字符不允许修改", groups = UpdateGroup.class)
    @Length(max = 100, message = "权限字符长度不可超过 100 字符")
    @Pattern(
            regexp = "^[a-zA-Z][a-zA-Z0-9:_-]{0,99}$",
            message = "权限字符需以字母开头，只能包含字母、数字、冒号、下划线、短横线",
            groups = AddGroup.class
    )
    private String roleKey;

    @Schema(description = "排序", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "排序为必填项")
    @Range(min = 0, max = 9999, message = "排序取值范围为 0-9999")
    private Integer sort;

    @Schema(description = "备注")
    @Length(max = 255, message = "备注长度不可超过 255 字符")
    private String remark;

    @Schema(description = "菜单ID列表")
    private Set<Long> menuIds;
}
