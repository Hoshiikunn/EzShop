package cn.hoshiikunn.ezshop.modules.sys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 修改用户名参数
 */
@Data
@Schema(description = "修改用户名参数")
public class SysProfileUsernameDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "新用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "新用户名为必填项")
    @Pattern(
            regexp = "^[a-zA-Z][a-zA-Z0-9_]{3,19}$",
            message = "用户名格式错误"
    )
    private String newUsername;
}
