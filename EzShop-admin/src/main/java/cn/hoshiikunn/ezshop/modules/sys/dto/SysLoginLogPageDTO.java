package cn.hoshiikunn.ezshop.modules.sys.dto;

import cn.hoshiikunn.ezshop.base.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Positive;
import java.util.Date;

/**
 * 登录日志分页条件查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "登录日志分页条件查询参数")
public class SysLoginLogPageDTO extends BasePageDTO {

    @Schema(description = "用户名")
    @Length(max = 64, message = "用户名长度不可超过 64 字符")
    private String username;

    @Schema(description = "用户ID")
    @Positive(message = "用户ID必须大于0")
    private Long userId;

    @Schema(description = "登录IP")
    @Length(max = 45, message = "登录IP长度不可超过 45 字符")
    private String ip;

    @Schema(description = "状态 0-失败 1-成功")
    @Range(min = 0, max = 1, message = "状态取值只能为 0(失败) 1(成功)")
    private Integer status;

    @Schema(description = "登录时间起")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeBegin;

    @Schema(description = "登录时间止")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;
}
