package cn.hoshiikunn.ezshop.modules.sys.dto;

import cn.hoshiikunn.ezshop.base.BasePageDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@Schema(description = "操作日志分页条件查询参数")
public class SysOperLogPageDTO extends BasePageDTO {

    @Schema(description = "所属模块")
    @Length(max = 50, message = "所属模块长度不可超过 50 字符")
    private String module;

    @Schema(description = "操作内容")
    @Length(max = 50, message = "操作内容长度不可超过 50 字符")
    private String operation;

    @Schema(description = "操作人员")
    @Length(max = 64, message = "操作人员长度不可超过 64 字符")
    private String username;

    @Schema(description = "操作IP")
    @Length(max = 45, message = "操作IP长度不可超过 45 字符")
    private String ip;

    @Schema(description = "状态 0-失败 1-成功")
    @Range(min = 0, max = 1, message = "状态取值只能为 0(失败) 1(成功)")
    private Integer status;

    @Schema(description = "操作时间起")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeBegin;

    @Schema(description = "操作时间止")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;
}
