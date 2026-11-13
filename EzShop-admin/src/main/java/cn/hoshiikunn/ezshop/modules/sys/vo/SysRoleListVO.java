package cn.hoshiikunn.ezshop.modules.sys.vo;

import cn.hoshiikunn.ezshop.modules.sys.entity.SysRoleEntity;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色列表信息
 */
@Data
@Schema(description = "角色列表信息")
public class SysRoleListVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色ID")
    private Long id;

    @Schema(description = "角色名称")
    private String roleName;

    @Schema(description = "角色权限字符")
    private String roleKey;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "状态 0-禁用 1-启用")
    private Integer status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    private Date createTime;

    @Schema(description = "更新时间")
    private Date updateTime;

    /**
     * 实体转列表 VO
     */
    public static SysRoleListVO of(SysRoleEntity entity) {
        return BeanUtil.toBean(entity, SysRoleListVO.class);
    }
}
