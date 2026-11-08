package cn.hoshiikunn.ezshop.modules.sys.dto;

import cn.hoshiikunn.ezshop.base.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 角色分页条件查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "角色分页条件查询参数")
public class SysRolePageDTO extends BasePageDTO {

    @Schema(description = "角色名称")
    @Length(max = 30, message = "角色名称长度不可超过 30 字符")
    private String roleName;

    @Schema(description = "角色权限字符")
    @Length(max = 100, message = "角色权限字符长度不可超过 100 字符")
    private String roleKey;

    @Schema(description = "状态 0-禁用 1-启用")
    @Range(min = 0, max = 1, message = "状态取值只能为 0(禁用) 1(启用)")
    private Integer status;

    @Schema(description = "创建时间起")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeBegin;

    @Schema(description = "创建时间止")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;
}
