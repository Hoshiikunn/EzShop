package cn.hoshiikunn.ezshop.modules.sys.dto;

import cn.hoshiikunn.ezshop.validation.group.AddGroup;
import cn.hoshiikunn.ezshop.validation.group.UpdateGroup;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * 用户参数
 */
@Data
@Schema(description = "用户参数")
public class SysUserDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    @Null(message = "用户ID必须为空", groups = AddGroup.class)
    @NotNull(message = "用户ID不能为空", groups = UpdateGroup.class)
    private Long id;

    @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "用户名为必填项")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]{3,19}$", message = "用户名格式错误")
    private String username;

    @Schema(description = "真实姓名", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "真实姓名为必填项")
    @Pattern(regexp = "^(?=.{2,15}$)\\p{IsHan}+(?:·\\p{IsHan}+)*$", message = "真实姓名格式错误")
    private String realName;

    @Schema(description = "头像")
    @Length(max = 255, message = "头像地址长度超限")
    private String avatarUrl;

    @Schema(description = "性别 0-女 1-男", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "性别为必填项")
    @Range(min = 0, max = 1, message = "性别取值只能为 0(女) 1(男)")
    private Integer gender;

    @Schema(description = "手机号")
    @Pattern(regexp = "^$|^1[3-9]\\d{9}$", message = "手机号格式错误")
    private String mobile;

    @Schema(description = "邮箱")
    @Email(message = "邮箱格式错误")
    @Length(max = 100, message = "邮箱长度超限")
    private String email;
}
