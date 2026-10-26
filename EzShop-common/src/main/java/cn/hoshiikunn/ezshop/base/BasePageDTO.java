package cn.hoshiikunn.ezshop.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

/**
 * 基础分页参数
 */
@Data
@Schema(description = "分页参数")
public class BasePageDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "页码，从 1 开始")
    @Min(value = 1, message = "页码最小为 1")
    private Integer current = 1;

    @Schema(description = "每页条数")
    @Min(value = 1, message = "每页条数最小为 1")
    @Max(value = 100, message = "每页条数最多 100")
    private Integer size = 10;
}
