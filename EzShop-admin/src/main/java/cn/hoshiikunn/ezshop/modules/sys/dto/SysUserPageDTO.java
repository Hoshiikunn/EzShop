package cn.hoshiikunn.ezshop.modules.sys.dto;

import cn.hoshiikunn.ezshop.base.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * 系统用户分页条件查询参数
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统用户分页条件查询参数")
public class SysUserPageDTO extends BasePageDTO {

    @Schema(description = "用户名")
    @Length(max = 20, message = "用户名长度超限")
    private String username;

    @Schema(description = "真实姓名")
    @Length(max = 32, message = "真实姓名长度超限")
    private String realName;

    @Schema(description = "性别 0-女 1-男")
    @Range(min = 0, max = 1, message = "性别范围 0-1")
    private Integer gender;

    @Schema(description = "手机号")
    @Pattern(regexp = "^$|^1\\d{10}$", message = "手机号格式错误")
    private String mobile;

    @Schema(description = "状态 0-禁用 1-启用")
    @Range(min = 0, max = 1, message = "状态范围 0-1")
    private Integer status;

    @Schema(description = "创建时间起")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeBegin;

    @Schema(description = "创建时间止")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;
}
