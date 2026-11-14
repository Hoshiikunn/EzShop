package cn.hoshiikunn.ezshop.modules.sys.vo;

import cn.hoshiikunn.ezshop.modules.sys.entity.SysMenuEntity;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
@Schema(description = "菜单简要信息")
public class SysMenuBriefVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单ID")
    private Long id;

    @Schema(description = "菜单名称")
    private String menuName;

    /**
     * 实体 转 VO
     */
    public static SysMenuBriefVO of(SysMenuEntity entity) {
        return BeanUtil.toBean(entity, SysMenuBriefVO.class);
    }
}