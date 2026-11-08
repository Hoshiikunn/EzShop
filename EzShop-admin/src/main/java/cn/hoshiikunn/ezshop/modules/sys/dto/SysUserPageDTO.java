package cn.hoshiikunn.ezshop.modules.sys.dto;

import cn.hoshiikunn.ezshop.base.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.Date;

/**
 * 系统用户分页条件查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统用户分页条件查询参数")
public class SysUserPageDTO extends BasePageDTO {

    @Schema(description = "用户ID")
    @Positive(message = "用户ID必须大于0")
    private Long id;

    @Schema(description = "用户名")
    @Length(max = 20, message = "用户名长度不可超过 20 字符")
    private String username;

    @Schema(description = "真实姓名")
    @Length(max = 32, message = "真实姓名长度不可超过 32 字符")
    private String realName;

    @Schema(description = "性别 0-女 1-男")
    @Range(min = 0, max = 1, message = "性别取值只能为 0(女) 1(男)")
    private Integer gender;

    @Schema(description = "手机号")
    @Pattern(regexp = "^$|^1\\d{10}$", message = "只支持中国大陆手机号")
    private String mobile;

    @Schema(description = "状态 0-禁用 1-启用")
    @Range(min = 0, max = 1, message = "状态取值只能为 0(禁用) 1(启用)")
    private Integer status;

    @Schema(description = "创建时间起")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeBegin;

    @Schema(description = "创建时间止")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;
}
