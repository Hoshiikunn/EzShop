package cn.hoshiikunn.ezshop.modules.sys.validator;

import cn.hoshiikunn.ezshop.exception.BusinessException;
import cn.hoshiikunn.ezshop.modules.sys.dto.SysMenuDTO;
import cn.hoshiikunn.ezshop.modules.sys.entity.SysMenuEntity;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Component;

import static cn.hoshiikunn.ezshop.common.result.AdminResultCode.MENU_PERMS_NOT_ALLOWED;
import static cn.hoshiikunn.ezshop.common.result.AdminResultCode.MENU_TYPE_MISMATCH;
import static cn.hoshiikunn.ezshop.modules.sys.enums.MenuTypeEnum.DIRECTORY;
import static cn.hoshiikunn.ezshop.modules.sys.enums.MenuTypeEnum.MENU;

/**
 * 菜单类型校验器
 */
@Component
public class MenuTypeValidator implements MenuValidator {

    @Override
    public String getMenuType() {
        return MENU.getValue();
    }

    @Override
    public void validateFields(SysMenuDTO dto) {
        // 菜单不可设置权限标识
        if (StrUtil.isNotBlank(dto.getPerms())) {
            throw new BusinessException(MENU_PERMS_NOT_ALLOWED);
        }
    }

    @Override
    public void validateOnCreate(SysMenuDTO dto, SysMenuEntity parent) {
        // 父菜单必须是目录类型
        if (!DIRECTORY.getValue().equals(parent.getMenuType())) {
            throw new BusinessException(MENU_TYPE_MISMATCH);
        }
    }

    @Override
    public void validateOnUpdate(SysMenuDTO dto, SysMenuEntity oldEntity, SysMenuEntity newParent) {
        // 更换父菜单时，新父菜单必须是目录类型
        if (newParent != null && !DIRECTORY.getValue().equals(newParent.getMenuType())) {
            throw new BusinessException(MENU_TYPE_MISMATCH);
        }
    }
}
