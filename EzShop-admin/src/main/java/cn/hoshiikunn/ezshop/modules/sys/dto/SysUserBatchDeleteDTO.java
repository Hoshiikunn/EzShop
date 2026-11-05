package cn.hoshiikunn.ezshop.modules.sys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Data
@Schema(description = "批量删除用户参数")
public class SysUserBatchDeleteDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "请选择要删除的用户")
    @Size(max = 100, message = "单次最多删除 100 个用户")
    private List<Long> ids;
}
