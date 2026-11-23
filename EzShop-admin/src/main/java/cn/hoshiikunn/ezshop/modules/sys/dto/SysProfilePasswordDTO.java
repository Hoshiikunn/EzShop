package cn.hoshiikunn.ezshop.modules.sys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 修改密码参数
 */
@Data
@Schema(description = "修改密码参数")
public class SysProfilePasswordDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "原密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "原密码为必填项")
    private String oldPassword;

    @Schema(description = "新密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "新密码为必填项")
    @Length(min = 8, max = 64, message = "新密码长度必须为 8-64 位")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)\\S{8,64}$",
            message = "新密码必须包含字母和数字，且不能包含空白字符"
    )
    private String newPassword;
}