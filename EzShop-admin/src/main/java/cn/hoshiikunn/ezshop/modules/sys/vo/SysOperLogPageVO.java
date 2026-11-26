package cn.hoshiikunn.ezshop.modules.sys.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志分页信息
 */
@Data
@Schema(description = "操作日志分页信息")
public class SysOperLogPageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "日志ID")
    private Long id;

    @Schema(description = "所属模块")
    private String module;

    @Schema(description = "操作内容")
    private String operation;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "请求方式")
    private String requestMethod;

    @Schema(description = "请求地址")
    private String requestUri;

    @Schema(description = "操作IP")
    private String ip;

    @Schema(description = "操作地点")
    private String operLocation;

    @Schema(description = "请求参数")
    private String requestParams;

    @Schema(description = "状态 0-失败 1-成功")
    private Integer status;

    @Schema(description = "失败原因")
    private String errorMsg;

    @Schema(description = "耗时(毫秒)")
    private Integer elapsedMs;

    @Schema(description = "操作时间")
    private Date createTime;

}
