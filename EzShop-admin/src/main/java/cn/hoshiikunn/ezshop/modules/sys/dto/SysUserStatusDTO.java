package cn.hoshiikunn.ezshop.modules.sys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户状态参数
 */
@Data
@Schema(description = "用户状态参数")
public class SysUserStatusDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "用户ID不能为空")
    private Long id;

    @Schema(description = "状态 0-禁用 1-启用", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "状态不能为空")
    @Range(min = 0, max = 1, message = "状态取值只能为 0(禁用) 1(启用)")
    private Integer status;
}
