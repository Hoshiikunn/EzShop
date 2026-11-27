package cn.hoshiikunn.ezshop.modules.sys.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志分页信息
 */
@Data
@Schema(description = "登录日志分页信息")
public class SysLoginLogPageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "日志ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "登录IP")
    private String ip;

    @Schema(description = "登录地点")
    private String loginLocation;

    @Schema(description = "操作系统")
    private String os;

    @Schema(description = "浏览器")
    private String browser;

    @Schema(description = "状态 0-失败 1-成功")
    private Integer status;

    @Schema(description = "结果描述")
    private String message;

    @Schema(description = "登录时间")
    private Date createTime;

}
