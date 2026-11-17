package cn.hoshiikunn.ezshop.modules.sys.validator;

import cn.hoshiikunn.ezshop.modules.sys.dto.SysMenuDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysMenuEntity;

/**
 * 菜单校验器策略接口
 */
public interface MenuValidator {

    /**
     * 获取该校验器支持的菜单类型
     *
     * @return 菜单类型值 (M-目录 C-菜单 F-权限)
     */
    String getMenuType();

    /**
     * 字段约束校验
     *
     * @param dto 菜单参数
     */
    void validateFields(SysMenuDTO dto);

    /**
     * 新增时的额外校验
     *
     * @param dto    菜单参数
     * @param parent 父菜单实体，根节点时为 {@code null}
     */
    default void validateOnCreate(SysMenuDTO dto, SysMenuEntity parent) {
    }

    /**
     * 修改时的额外校验
     *
     * @param dto       菜单参数
     * @param oldEntity 原菜单实体
     * @param newParent 新父菜单实体，未修改父ID时为 {@code null}
     */
    default void validateOnUpdate(SysMenuDTO dto, SysMenuEntity oldEntity, SysMenuEntity newParent) {
    }
}
