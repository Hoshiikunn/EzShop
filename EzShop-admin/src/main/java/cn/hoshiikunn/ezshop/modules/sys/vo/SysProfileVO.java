package cn.hoshiikunn.ezshop.modules.sys.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 个人资料
 */
@Data
@Schema(description = "个人资料")
public class SysProfileVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "真实姓名")
    private String realName;

    @Schema(description = "头像地址")
    private String avatarUrl;

    @Schema(description = "性别 0-女 1-男")
    private Integer gender;

    @Schema(description = "手机号")
    private String mobile;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "最后登录IP地址")
    private String lastLoginIp;

    @Schema(description = "最后登录时间")
    private Date lastLoginTime;

    @Schema(description = "账号创建时间")
    private Date createTime;
}
