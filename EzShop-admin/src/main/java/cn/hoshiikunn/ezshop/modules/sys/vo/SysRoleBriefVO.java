package cn.hoshiikunn.ezshop.modules.sys.vo;

import cn.hoshiikunn.ezshop.modules.sys.entity.SysRoleEntity;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "角色简要信息")
public class SysRoleBriefVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "角色ID")
    private Long id;

    @Schema(description = "角色名称")
    private String roleName;

    /**
     * 实体 转 VO
     */
    public static SysRoleBriefVO of(SysRoleEntity entity) {
        return BeanUtil.toBean(entity, SysRoleBriefVO.class);
    }
}
