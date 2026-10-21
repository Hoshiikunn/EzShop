package cn.hoshiikunn.ezshop.modules.sys.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录令牌
 */
@Data
@Schema(description = "登录令牌")
public class LoginTokenVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "令牌")
    private String token;

    @Schema(description = "过期时间")
    private Date expiresAt;
}
