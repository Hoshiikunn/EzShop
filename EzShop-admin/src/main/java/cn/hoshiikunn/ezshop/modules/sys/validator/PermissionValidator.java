package cn.hoshiikunn.ezshop.modules.sys.validator;

import cn.hoshiikunn.ezshop.exception.BusinessException;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysMenuDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysMenuEntity;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import static cn.hoshiikunn.ezshop.common.result.AdminResultCode.*;
import static cn.hoshiikunn.ezshop.modules.sys.enums.MenuTypeEnum.PERMISSION;
import static cn.hoshiikunn.ezshop.modules.sys.enums.MenuTypeEnum.MENU;

/**
 * 权限节点校验器
 */
@Component
public class PermissionValidator implements MenuValidator {

    @Override
    public String getMenuType() {
        return PERMISSION.getValue();
    }

    @Override
    public void validateFields(SysMenuDTO dto) {
        // 必须设置权限标识
        if (StrUtil.isBlank(dto.getPerms())) {
            throw new BusinessException(MENU_PERMS_REQUIRED, "权限节点必须设置权限标识");
        }
        // 权限节点不可设置路由地址
        if (StrUtil.isNotBlank(dto.getPath())) {
            throw new BusinessException(MENU_PATH_NOT_ALLOWED, "权限节点不可设置路由地址");
        }
        // 权限节点不可设置组件路径
        if (StrUtil.isNotBlank(dto.getComponent())) {
            throw new BusinessException(MENU_COMPONENT_NOT_ALLOWED, "权限节点不可设置组件路径");
        }
        // 权限节点不可设置可见性
        if (dto.getVisible() != null) {
            throw new BusinessException(MENU_VISIBLE_NOT_ALLOWED, "权限节点不可设置可见性");
        }
        // 权限节点不可设置打开方式
        if (dto.getOpenStyle() != null) {
            throw new BusinessException(MENU_OPEN_STYLE_NOT_ALLOWED, "权限节点不可设置打开方式");
        }
    }

    @Override
    public void validateOnCreate(SysMenuDTO dto, SysMenuEntity parent) {
        // 父菜单必须是菜单类型
        if (!MENU.getValue().equals(parent.getMenuType())) {
            throw new BusinessException(MENU_TYPE_MISMATCH);
        }
    }

    @Override
    public void validateOnUpdate(SysMenuDTO dto, SysMenuEntity oldEntity, SysMenuEntity newParent) {
        // 更换父菜单时，新父菜单必须是菜单类型
        if (newParent != null && !MENU.getValue().equals(newParent.getMenuType())) {
            throw new BusinessException(MENU_TYPE_MISMATCH);
        }
    }
}
