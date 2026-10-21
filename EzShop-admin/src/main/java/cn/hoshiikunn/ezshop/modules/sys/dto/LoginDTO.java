package cn.hoshiikunn.ezshop.modules.sys.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 账号密码登录参数
 */
@Data
@Schema(description = "账号密码登录参数")
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名为必填项")
    @Length(max = 20, message = "用户名或密码错误")
    private String username;

    @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "密码为必填项")
    @Length(max = 32, message = "用户名或密码错误")
    private String password;
}
