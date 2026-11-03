package cn.hoshiikunn.ezshop.modules.sys.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * 系统用户初始化值
 */
@Data
@Schema(description = "系统用户初始化值")
public class SysUserInitVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "初始密码")
    private String initPassword;
}
